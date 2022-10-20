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
        String[] variableTokens = matchSplitError(line.strip(), splitNoCutBack(type), 2);
        int count = accessCount(variableTokens[0]);
        variableTokens[0] = variableTokens[0].substring(count);
        if (count > repositoryArray.length) throw VariableException.localNoVariable();
        var repository = repositoryArray[count];
        for (var entry : repository.entrySet()) {
            var map = entry.getValue();
            if (map.containsKey(variableTokens[0])) {
                map.put(variableTokens[0], variableTokens[1]);
                return;
            }
        }
    }

    @Override
    public void first() {

    }
}
