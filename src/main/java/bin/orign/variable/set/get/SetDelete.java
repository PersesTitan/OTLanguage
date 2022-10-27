package bin.orign.variable.set.get;

import bin.exception.VariableException;
import bin.token.LoopToken;
import work.StartWork;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SetDelete implements StartWork, LoopToken {
    private final String type;
    private final Matcher matcher;

    public SetDelete(String type) {
        this.type = type;
        String patternText = startEndMerge(VARIABLE_ACCESS, type, "\\d+");
        this.matcher = Pattern.compile(patternText).matcher("");
    }

    @Override
    public boolean check(String line) {
        return matcher.reset(line).find();
    }

    @Override
    public void start(String line, String origen,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        line = line.strip();
        int count = accessCount(line, repositoryArray.size());
        if (count == -1) throw VariableException.localNoVariable();
        line = line.substring(count);   // ~변수-1 -> 변수-1
        String[] tokens = matchSplitError(line, type + "(?=\\d+$)", 2);
        var repository = repositoryArray.get(count);
        getSet(repository, tokens[0], Integer.parseInt(tokens[1]));
    }

    @Override
    public void first() {
        
    }

    private void getSet(Map<String, Map<String, Object>> repository,
                         String variableName, int position) {
        for (var token : SET_LIST) {
            var rep = repository.get(token);
            if (rep.containsKey(variableName)) {
                LinkedHashSet<Object> set = (LinkedHashSet<Object>) rep.get(variableName);
                set.remove(new ArrayList<>(set).get(position));
                return;
            }
        }
    }
}
