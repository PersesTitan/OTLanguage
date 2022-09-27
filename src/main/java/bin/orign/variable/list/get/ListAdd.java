package bin.orign.variable.list.get;

import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.VariableToken;
import work.StartWork;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static bin.check.VariableCheck.*;

public class ListAdd implements
        VariableToken, StartWork, GetList {
    private final String type;
    private final Pattern pattern;

    public ListAdd(String type) {
        String patternText = startMerge(VARIABLE_ACCESS, type, "[^" + type + "].*");
        this.pattern = Pattern.compile(patternText);
        this.type = type;
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {
        line = line.strip();
        int count = accessCount(line);
        line = line.replaceFirst(START + ACCESS + "+", "");
        if (count > repositoryArray.length) throw VariableException.localNoVariable();
        var repository = repositoryArray[count];

        String[] vs = line.split(type, 2);
        String variableName = vs[0].strip();
        String variableValue = vs[1].strip();
        for (Map.Entry<String, Map<String, Object>> entry : repository.entrySet()) {
            var values = entry.getValue();
            if (values.containsKey(variableName)) {
                if (!LIST_LIST.contains(entry.getKey())) throw MatchException.grammarError();
                LinkedList<Object> list = (LinkedList<Object>) values.get(variableName);
                if (!isListString(variableValue) && getType(entry.getKey(), variableValue)) list.add(variableValue);
                else list.addAll(getValues(entry.getKey(), variableValue));
                return;
            }
        }
        throw VariableException.noDefine();
    }

    private LinkedList<?> getValues(String type, String value) {
        return switch (type) {
            case SET_INTEGER -> getIntegerList(value);
            case SET_LONG -> getLongList(value);
            case SET_BOOLEAN -> getBoolList(value);
            case SET_CHARACTER -> getCharacterList(value);
            case SET_FLOAT -> getFlotList(value);
            case SET_DOUBLE -> getDoubleList(value);
            default -> getStringList(value);
        };
    }

    private boolean getType(String type, String value) {
        return switch (type) {
            case SET_INTEGER -> isInteger(value);
            case SET_LONG -> isLong(value);
            case SET_BOOLEAN -> isBoolean(value);
            case SET_CHARACTER -> isCharacter(value);
            case SET_FLOAT -> isFloat(value);
            case SET_DOUBLE -> isDouble(value);
            default -> true;
        };
    }
}

