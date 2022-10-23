package bin.orign.variable.list.get;

import bin.exception.VariableException;
import bin.token.LoopToken;
import work.ReturnWork;

import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListGet implements ReturnWork, LoopToken {
    private final String type;
    private final Matcher matcher;

    public ListGet(String type) {
        this.type = type;
        String patternText = merge(VARIABLE_GET_S, VARIABLE_ACCESS, type, "\\d+", VARIABLE_GET_E);
        this.matcher = Pattern.compile(patternText).matcher("");
    }

    @Override
    public boolean check(String line) {
        return (matcher.reset(line)).find();
    }

    @Override
    public String start(String line, Map<String, Map<String, Object>>[] repositoryArray) {
        matcher.reset();
        while (matcher.find()) {
            String group = matcher.group(); // :변수>>1_
            String cut = bothEndCut(group); // 변수>>1
            int count = accessCount(cut, repositoryArray.length);   // 0
            if (count == -1) continue;
            var repository = repositoryArray[count];
            // 변수, 1
            String[] tokens = matchSplitError(cut.substring(count), type, 2);
            String value = getValue(repository, Integer.parseInt(tokens[1]), tokens[0]);
            if (value != null) line = line.replace(group, value);
        }
        return line;
    }

    private String getValue(Map<String, Map<String, Object>> repository, int count, String variableName) {
        for (String lists : LIST_LIST) {
            var rep = repository.get(lists);
            if (rep.containsKey(variableName)) {
                LinkedList<Object> list = (LinkedList<Object>) rep.get(variableName);
                if (list.size() > count) return list.get(count).toString();
            }
        }
        return null;
    }

    @Override
    public ReturnWork first() {
        return this;
    }
}
