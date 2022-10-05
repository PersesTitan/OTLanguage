package bin.orign.variable.map.get;

import bin.exception.VariableException;
import bin.token.LoopToken;
import work.StartWork;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapDelete implements StartWork, LoopToken {
    private final String type;
    private final Matcher matcher;

    public MapDelete(String type) {
        this.type = type;
        String patternText = startEndMerge(VARIABLE_ACCESS, type, "[\\d\\D]+");
        this.matcher = Pattern.compile(patternText).matcher("");
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
        if (repositoryArray.length < count) throw VariableException.localNoVariable();
        // ~변수---1 -> 변수---1 -> 변수, 1
        String[] tokens = matchSplitError(line.substring(count), Pattern.quote(type), 2);
        var repository = repositoryArray[count];
        getMap(repository, tokens[0], tokens[1]);
    }

    private void getMap(Map<String, Map<String, Object>> repository,
                        String variableName, String value) {
        for (var token : MAP_LIST) {
            var rep = repository.get(token);
            if (rep.containsKey(variableName)) {
                LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) rep.get(variableName);
                map.remove(value);
                return;
            }
        }
    }
}
