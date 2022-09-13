package bin.orign.variable.set.get;

import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.VariableToken;
import work.StartWork;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class SetClear implements
        VariableToken, StartWork {
    private final String type;
    private final Pattern pattern;

    public SetClear(String type) {
        String patternText = startEndMerge(VARIABLE_NAME, type);
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

        String variableName = line.replaceFirst(type + END, "");
        if (count > repositoryArray.length) throw VariableException.localNoVariable();
        Set<Object> set = getSet(count, variableName, repositoryArray);
        set.clear();
    }

    private Set<Object> getSet(int count, String variableName,
                               Map<String, Map<String, Object>>[] repositoryArray) {
        var repository = repositoryArray[count];
        for (Map.Entry<String, Map<String, Object>> entry : repository.entrySet()) {
            Map<String, Object> values = entry.getValue();
            if (values.containsKey(variableName)) {
                if (!SET_LIST.contains(entry.getKey())) throw MatchException.grammarError();
                return (Set<Object>) values.get(variableName);
            }
        }
        throw VariableException.noDefine();
    }

    private int accessCount(String line) {
        int count = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ACCESS.charAt(0)) count++;
            else break;
        }
        return count;
    }
}
