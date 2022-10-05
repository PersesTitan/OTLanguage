package bin.orign.variable.set.get;

import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.Token;
import bin.token.VariableToken;
import work.StartWork;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetSort implements StartWork, Token, VariableToken {
    private final String type;
    private final Matcher matcher;

    public SetSort(String type) {
        String patternText = startEndMerge(VARIABLE_NAME, type);
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
        String variableName = line
                .replaceFirst(START + ACCESS + "+", "")
                .replaceFirst(type + END, "");

        if (count > repositoryArray.length) throw VariableException.localNoVariable();
        var repository = repositoryArray[count];
        for (Map.Entry<String, Map<String, Object>> entry : repository.entrySet()) {
            Map<String, Object> values = entry.getValue();
            if (values.containsKey(variableName)) {
                if (!SET_LIST.contains(entry.getKey())) throw MatchException.grammarError();
                LinkedHashSet<Object> set = (LinkedHashSet<Object>) values.get(variableName);
                TreeSet<Object> treeSet = new TreeSet<>(set);
                set.clear();
                set.addAll(treeSet);
                return;
            }
        }
        throw VariableException.noDefine();
    }
}
