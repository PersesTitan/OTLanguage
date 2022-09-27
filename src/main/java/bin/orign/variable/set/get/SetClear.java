package bin.orign.variable.set.get;

import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.VariableToken;
import work.StartWork;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class SetClear implements
        VariableToken, StartWork {
    private final String type;
    private final Pattern pattern;

    public SetClear(String type) {
        String patternText = startEndMerge(VARIABLE_ACCESS, type);
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
        LinkedHashSet<Object> set = getSet(count, variableName, repositoryArray);
        set.clear();
    }

    private LinkedHashSet<Object> getSet(int count, String variableName,
                               Map<String, Map<String, Object>>[] repositoryArray) {
        var repository = repositoryArray[count];
        for (Map.Entry<String, Map<String, Object>> entry : repository.entrySet()) {
            Map<String, Object> values = entry.getValue();
            if (values.containsKey(variableName)) {
                if (!SET_LIST.contains(entry.getKey())) throw MatchException.grammarError();
                return (LinkedHashSet<Object>) values.get(variableName);
            }
        }
        throw VariableException.noDefine();
    }
}
