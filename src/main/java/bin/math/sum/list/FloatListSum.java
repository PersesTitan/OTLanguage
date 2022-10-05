package bin.math.sum.list;

import bin.orign.variable.list.get.GetList;
import bin.token.LoopToken;
import work.ReturnWork;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FloatListSum implements ReturnWork, LoopToken, GetList {
    private final String type = FLOAT_VARIABLE;
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
                var repository1 = repositoryArray[accessCount].get(LIST_DOUBLE);
                var repository2 = repositoryArray[accessCount].get(LIST_FLOAT);
                String variableName = groups.substring(accessCount);
                if (repository1.containsKey(variableName)) {
                    LinkedList<Double> list = (LinkedList<Double>) repository1.get(variableName);
                    String sum = Double.toString(list.stream().mapToDouble(v -> v).sum());
                    line = line.replace(group, sum);
                } else if (repository2.containsKey(variableName)) {
                    LinkedList<Float> list = (LinkedList<Float>) repository2.get(variableName);
                    String sum = Double.toString(list.stream().mapToDouble(v -> v).sum());
                    line = line.replace(group, sum);
                }
            } else {
                // 변환이 불가능하면 에러발생
                try {
                    LinkedList<Double> list = getDoubleList(groups);
                    String sum = Double.toString(list.stream().mapToDouble(v -> v).sum());
                    line = line.replace(group, sum);
                } catch (Exception ignored) {}
            }
        }
        return line;
    }
}
