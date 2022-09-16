package bin.math.sum.set;

import bin.orign.variable.list.get.GetList;
import bin.token.LoopToken;
import work.ReturnWork;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LongSetSum implements ReturnWork, LoopToken, GetList {
    private final String patternText =
            VARIABLE_GET_S + VARIABLE_ACCESS + ACCESS + LIST_SUM + VARIABLE_GET_E;
    private final Pattern pattern = Pattern.compile(patternText);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public String start(String line,
                        Map<String, Map<String, Object>>[] repositoryArray) {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String group = matcher.group();
            String groups = bothEndCut(group)
                    .replaceFirst(ACCESS + SET_SUM + END, "");
            if (groups.matches(VARIABLE_ACCESS)) {
                var repository = repositoryArray[accessCount(groups)].get(SET_LONG);
                String variableName = groups.replaceAll(ACCESS, "");
                if (repository.containsKey(variableName)) {
                    Set<Long> list = (Set<Long>) repository.get(variableName);
                    String sum = Long.toString(list.stream().mapToLong(v -> v).sum());
                    line = line.replace(group, sum);
                }
            }
        }
        return line;
    }
}
