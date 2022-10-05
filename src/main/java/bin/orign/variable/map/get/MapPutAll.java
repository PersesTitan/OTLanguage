package bin.orign.variable.map.get;

import bin.exception.VariableException;
import bin.token.LoopToken;
import work.StartWork;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapPutAll implements StartWork, LoopToken, GetMap {
    private final String type;
    private final Matcher matcher;

    public MapPutAll(String type) {
        this.type = type;
        String patternText = startEndMerge(VARIABLE_ACCESS, type, "[^", type.charAt(0) + "]+", "[\\d\\D]*");
        this.matcher = Pattern.compile(patternText).matcher("");
    }

    @Override
    public boolean check(String line) {
        return matcher.reset(line).find();
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {
        String[] variableTokens = matchSplitError(line.strip(), type, 2);
        int count = accessCount(variableTokens[0]);
        variableTokens[0] = variableTokens[0].substring(count);
        if (count > repositoryArray.length) throw VariableException.localNoVariable();
        var repository = repositoryArray[count];

        for (String variable : MAP_LIST) {
            var rep = repository.get(variable);
            if (rep.containsKey(variableTokens[0])) {
                LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) rep.get(variableTokens[0]);
                addVariable(variable, map, variableTokens[1]);
                return;
            }
        }
    }

    private void addVariable(String variableType, LinkedHashMap<String, Object> map, String value) {
        switch (variableType) {
            case MAP_INTEGER -> map.putAll(getIntegerMap(value));
            case MAP_LONG -> map.putAll(getLongMap(value));
            case MAP_BOOLEAN -> map.putAll(getBoolMap(value));
            case MAP_STRING -> map.putAll(getStringMap(value));
            case MAP_CHARACTER -> map.putAll(getCharacterMap(value));
            case MAP_FLOAT -> map.putAll(getFlotMap(value));
            case MAP_DOUBLE -> map.putAll(getDoubleMap(value));
        }
    }
}
