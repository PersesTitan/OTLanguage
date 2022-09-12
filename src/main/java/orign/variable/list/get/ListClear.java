package orign.variable.list.get;

import exception.MatchException;
import exception.VariableException;
import token.VariableToken;
import work.StartWork;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class ListClear implements
        VariableToken, StartWork {
    private final String type;
    private final Pattern pattern;

    public ListClear(String type) {
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
        getList(count, variableName, repositoryArray).clear();;
    }

    private List<Object> getList(int count, String variableName,
                               Map<String, Map<String, Object>>[] repositoryArray) {
        var repository = repositoryArray[count];
        for (Map.Entry<String, Map<String, Object>> entry : repository.entrySet()) {
            Map<String, Object> values = entry.getValue();
            if (values.containsKey(variableName)) {
                if (!LIST_LIST.contains(entry.getKey())) throw MatchException.grammarError();
                return (List<Object>) values.get(variableName);
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
