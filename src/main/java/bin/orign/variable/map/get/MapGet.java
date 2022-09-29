package bin.orign.variable.map.get;

import bin.token.VariableToken;
import work.ReturnWork;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapGet implements ReturnWork, VariableToken {
    private final String type;
    private final Pattern pattern;
    private Matcher matcher;

    public MapGet(String type) {
        this.type = type;
        String token = "[^" + type.charAt(0) + VARIABLE_GET_E + "]+" + "[^" + VARIABLE_GET_E + "]*";
        String patternText = VARIABLE_GET_S + VARIABLE_ACCESS + type + token + VARIABLE_GET_E;
        this.pattern = Pattern.compile(patternText);
    }

    @Override
    public boolean check(String line) {
        matcher = pattern.matcher(line);
        return matcher.find();
    }

    @Override
    public String start(String line,
                        Map<String, Map<String, Object>>[] repositoryArray) {
        matcher.reset();
        while (matcher.find()) {
            String group = matcher.group(); // :~변수>>키 값_
            String[] variables = matchSplitError(bothEndCut(group), type, 2);
            int count = accessCount(variables[0]);
            var repository = repositoryArray[count];
            String variableName = variables[0].substring(count); // 변수
            line = getMap(repository, group, line, variableName, variables[1]);
        }
        return line;
    }

    private String getMap(Map<String, Map<String, Object>> repository,
                          String group, String line,
                          String variableName, String key) {
        for (var type : MAP_LIST) {
            var rep = repository.get(type);
            if (rep.containsKey(variableName)) {
                LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) rep.get(variableName);
                if (map.containsKey(key)) line = line.replace(group, map.get(key).toString());
            }
        }
        return line;
    }
}
