package bin.orign.variable.list.get;

import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.VariableToken;
import work.StartWork;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bin.check.VariableCheck.*;

public class ListAdd implements VariableToken, StartWork, GetList {
    private final String type;
    private final Matcher matcher;

    public ListAdd(String type) {
        String patternText = startMerge(VARIABLE_ACCESS, BLANK, type, "[^" + type + "].*");
        this.matcher = Pattern.compile(patternText).matcher("");
        this.type = type;
    }

    @Override
    public boolean check(String line) {
        return matcher.reset(line).find();
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {
        line = line.strip();
        int count = accessCount(line);
        if (count > repositoryArray.length) throw VariableException.localNoVariable();
        var repository = repositoryArray[count];

        String[] vs = line.substring(count).split(type, 2);
        String variableName = vs[0].strip();
        String variableValue = vs[1].strip();
        for (Map.Entry<String, Map<String, Object>> entry : repository.entrySet()) {
            var values = entry.getValue();
            if (values.containsKey(variableName)) {
                if (!LIST_LIST.contains(entry.getKey())) throw MatchException.grammarError();
                LinkedList<Object> list = (LinkedList<Object>) values.get(variableName);
                if (!isListString(variableValue)) list.add(getType(entry.getKey(), variableValue));
                else list.addAll(getValues(entry.getKey(), variableValue));
                return;
            }
        }
        throw VariableException.noDefine();
    }

    @Override
    public void first() {

    }

    private LinkedList<?> getValues(String type, String value) {
        return switch (type) {
            case LIST_INTEGER -> getIntegerList(value);
            case LIST_LONG -> getLongList(value);
            case LIST_BOOLEAN -> getBoolList(value);
            case LIST_CHARACTER -> getCharacterList(value);
            case LIST_FLOAT -> getFlotList(value);
            case LIST_DOUBLE -> getDoubleList(value);
            default -> getStringList(value);
        };
    }

    private Object getType(String type, String value) {
        switch (type) {
            case LIST_INTEGER:
                if (isInteger(value)) return Integer.parseInt(value);
                else throw VariableException.typeMatch();
            case LIST_LONG:
                if (isLong(value)) return Long.parseLong(value);
                else throw VariableException.typeMatch();
            case LIST_BOOLEAN:
                if (isBoolean(value)) return value;
                else throw VariableException.typeMatch();
            case LIST_CHARACTER:
                if (isCharacter(value)) return value.charAt(0);
                else throw VariableException.typeMatch();
            case LIST_FLOAT:
                if (isFloat(value)) return Float.parseFloat(value);
                else throw VariableException.typeMatch();
            case LIST_DOUBLE:
                if (isDouble(value)) return Double.parseDouble(value);
                else throw VariableException.typeMatch();
            default: return value;
        }
    }
}

