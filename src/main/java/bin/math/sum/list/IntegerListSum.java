package bin.math.sum.list;

import bin.orign.variable.list.get.GetList;
import bin.token.LoopToken;
import work.ReturnWork;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntegerListSum implements ReturnWork, LoopToken, GetList {
    private final String type = LIST_INTEGER;
    private final String value = orMerge(VARIABLE_ACCESS, NUMBER_LIST);
    private final String patternText =
            VARIABLE_GET_S + value + LIST_SUM + VARIABLE_GET_E;
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
                    .replaceFirst(LIST_SUM + END, "");
            if (groups.matches(VARIABLE_ACCESS)) {
                var repository = repositoryArray[accessCount(groups)].get(type);
                String variableName = groups.replaceAll(ACCESS, "");
                if (repository.containsKey(variableName)) {
                    LinkedList<Integer> list = (LinkedList<Integer>) repository.get(variableName);
                    String sum = Integer.toString(list.stream().mapToInt(v -> v).sum());
                    line = line.replace(group, sum);
                }
            } else {
                try {
                    LinkedList<Integer> list = getIntegerList(groups);
                    String sum = Integer.toString(list.stream().mapToInt(v -> v).sum());
                    line = line.replace(group, sum);
                } catch (Exception ignored) {}
            }
        }
        return line;
    }
}
