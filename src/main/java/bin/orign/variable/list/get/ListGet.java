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
    private final Pattern pattern;
    private Matcher matcher;

    public ListGet(String type) {
        this.type = type;
        String patternText = merge(VARIABLE_GET_S, VARIABLE_ACCESS, type, "\\d+", VARIABLE_GET_E);
        this.pattern = Pattern.compile(patternText);
    }

    @Override
    public boolean check(String line) {
        matcher = pattern.matcher(line);
        return matcher.find();
    }

    @Override
    public String start(String line, Map<String, Map<String, Object>>[] repositoryArray) {
        matcher.reset();
        while (matcher.find()) {
            String group = matcher.group();
            String cut = bothEndCut(group);
            int count = accessCount(cut);
            if (repositoryArray.length < count) throw VariableException.localNoVariable();
            cut = cut.substring(count); // 변수>>1
            String[] tokens = matchSplitError(cut, Pattern.quote(type), 2);
            var repository = repositoryArray[count];

            line = getValue(repository, Integer.parseInt(tokens[1].strip()), group, line, tokens[0]);
        }
        return line;
    }

    private String getValue(Map<String, Map<String, Object>> repository,
                            int count, String group,
                            String line, String variableName) {
        for (var rep : repository.values()) {
            if (rep.containsKey(variableName)) {
                LinkedList<Object> list = (LinkedList<Object>) rep.get(variableName);
                if (list.size() > count) return line.replace(group, list.get(count).toString());
            }
        }
        return line;
    }
}
