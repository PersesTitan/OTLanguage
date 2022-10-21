package bin.math.sum.set;

import bin.orign.variable.list.get.GetList;
import bin.orign.variable.set.get.GetSet;
import bin.token.LoopToken;
import work.ReturnWork;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntegerSetSum implements ReturnWork, LoopToken, GetSet {
    private final String value = orMerge(VARIABLE_ACCESS, NUMBER_LIST);
    private final String patternText =
            VARIABLE_GET_S + value + SET_SUM + VARIABLE_GET_E;
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
                    .replaceFirst(SET_SUM + END, "");
            if (groups.matches(VARIABLE_ACCESS)) {
                var repository = repositoryArray[accessCount(groups)].get(SET_INTEGER);
                String variableName = groups.replaceAll(ACCESS, "");
                if (repository.containsKey(variableName)) {
                    LinkedHashSet<Integer> list = (LinkedHashSet<Integer>) repository.get(variableName);
                    String sum = Integer.toString(list.stream().mapToInt(v -> v).sum());
                    line = line.replace(group, sum);
                }
            } else {
                // 변환이 불가능하면 에러발생
                try {
                    LinkedHashSet<Integer> list = getIntegerSet(groups);
                    String sum = Integer.toString(list.stream().mapToInt(v -> v).sum());
                    line = line.replace(group, sum);
                } catch (Exception ignored) {}
            }
        }
        return line;
    }

    @Override
    public ReturnWork first() {
        return this;
    }
}