package bin.orign.variable.map.get;

import bin.exception.VariableException;
import bin.token.VariableToken;
import work.ReturnWork;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapGet implements ReturnWork, VariableToken {
    private final String type;
    private final Matcher matcher;

    public MapGet(String type) {
        this.type = type;
        String token = "[^" + type.charAt(0) + VARIABLE_GET_E + "]+" + "[^" + VARIABLE_GET_E + "]*";
        String patternText = VARIABLE_GET_S + VARIABLE_ACCESS + type + token + VARIABLE_GET_E;
        this.matcher = Pattern.compile(patternText).matcher("");
    }

    @Override
    public boolean check(String line) {
        return (matcher.reset(line)).find();
    }

    @Override
    public String start(String line,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        matcher.reset();
        while (matcher.find()) {
            String group = matcher.group(); // :~변수>>키 값_
            String[] variables = matchSplitError(bothEndCut(group), type, 2);
            int count = accessCount(variables[0], repositoryArray.size());
            if (count == -1) continue;
            var repository = repositoryArray.get(count);
            String value = getMap(repository, variables[1], variables[0].substring(count));
            if (value != null) line = line.replace(group, value);
        }
        return line;
    }

    private String getMap(Map<String, Map<String, Object>> repository, String key, String variableName) {
        for (String lists : MAP_LIST) {
            var rep = repository.get(lists);
            if (rep.containsKey(variableName)) {
                LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) rep.get(variableName);
                if (map.containsKey(key)) return map.get(key).toString();
            }
        }
        return null;
    }

    @Override
    public ReturnWork first() {
        return this;
    }
}
