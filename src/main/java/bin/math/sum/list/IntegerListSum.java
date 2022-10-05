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
    private final String value = orMerge(VARIABLE_ACCESS, NUMBER_LIST);
    private final String patternText =
            VARIABLE_GET_S + value + LIST_SUM + VARIABLE_GET_E;
    private final Matcher matcher = Pattern.compile(patternText).matcher("");

    @Override
    public boolean check(String line) {
        return (matcher.reset(line)).find();
    }

    @Override
    public String start(String line,
                        Map<String, Map<String, Object>>[] repositoryArray) {
        matcher.reset();
        while (matcher.find()) {
            String group = matcher.group();
            String groups = bothEndCut(group)
                    .replaceFirst(LIST_SUM + END, "");
            if (groups.matches(VARIABLE_ACCESS)) {
                int accessCount = accessCount(groups);
                if (repositoryArray.length <= accessCount) continue;
                var repository = repositoryArray[accessCount].get(LIST_INTEGER);
                String variableName = groups.substring(accessCount);
                if (repository.containsKey(variableName)) {
                    LinkedList<Integer> list = (LinkedList<Integer>) repository.get(variableName);
                    String sum = Integer.toString(list.stream().mapToInt(v -> v).sum());
                    line = line.replace(group, sum);
                }
            } else {
                // 변환이 불가능하면 에러발생
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
