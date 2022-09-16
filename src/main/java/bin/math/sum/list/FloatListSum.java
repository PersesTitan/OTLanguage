package bin.math.sum.list;

import bin.orign.variable.list.get.GetList;
import bin.token.LoopToken;
import work.ReturnWork;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FloatListSum implements ReturnWork, LoopToken, GetList {
    private final String type = FLOAT_VARIABLE;
    private final String value = orMerge(VARIABLE_ACCESS, NUMBER_LIST);
    private final String patternText =
            VARIABLE_GET_S + value + ACCESS + LIST_SUM + VARIABLE_GET_E;
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
                    .replaceFirst(ACCESS + LIST_SUM + END, "");
            if (groups.matches(VARIABLE_ACCESS)) {
                var repository1 = repositoryArray[accessCount(groups)].get(FLOAT_VARIABLE);
                var repository2 = repositoryArray[accessCount(groups)].get(DOUBLE_VARIABLE);
                String variableName = groups.replaceAll(ACCESS, "");
                if (repository1.containsKey(variableName)) {
                    List<Double> list = (List<Double>) repository1.get(variableName);
                    String sum = Double.toString(list.stream().mapToDouble(v -> v).sum());
                    line = line.replace(group, sum);
                } else if (repository2.containsKey(variableName)) {
                    List<Float> list = (List<Float>) repository2.get(variableName);
                    String sum = Double.toString(list.stream().mapToDouble(v -> v).sum());
                    line = line.replace(group, sum);
                }
            } else {
                try {
                    List<Double> list = getDoubleList(groups);
                    String sum = Double.toString(list.stream().mapToDouble(v -> v).sum());
                    line = line.replace(group, sum);
                } catch (Exception ignored) {}
            }
        }
        return line;
    }
}
