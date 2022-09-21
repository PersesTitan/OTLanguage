package bin.orign.variable;

import bin.token.VariableToken;
import work.ReturnWork;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetVariable implements ReturnWork, VariableToken {
    private final static Pattern pattern = Pattern.compile(VARIABLE);
    private Matcher matcher;

    @Override
    public boolean check(String line) {
        return (matcher = pattern.matcher(line)).find();
    }

    @Override
    public String start(String line,
                        Map<String, Map<String, Object>>[] repositoryArray) {
        matcher.reset();
        while (matcher.find()) {
            String group = matcher.group();
            int access = accessCount(group);
            String variableName = bothEndCut(group).replaceFirst(START + ACCESS + "+", "");
            var repository = repositoryArray[access];
            line = getValue(group, variableName, line, repository);
        }

        return line;
    }


    private String getValue(String group, String variableName, String line,
                            Map<String, Map<String, Object>> repository) {
        for (var rep : repository.values()) {
            if (rep.containsKey(variableName))
                line = line.replaceFirst(Pattern.quote(group), rep.get(variableName).toString());
        }
        return line;
    }
}
