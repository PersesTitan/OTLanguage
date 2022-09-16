package bin.math.sum.set;

import bin.orign.variable.list.get.GetList;
import bin.token.LoopToken;
import work.ReturnWork;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FloatSetSum implements ReturnWork, LoopToken, GetList {
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
                var repository1 = repositoryArray[accessCount(groups)].get(SET_FLOAT);
                var repository2 = repositoryArray[accessCount(groups)].get(SET_DOUBLE);
                String variableName = groups.replaceAll(ACCESS, "");
                if (repository1.containsKey(variableName)) {
                    Set<Float> list = (Set<Float>) repository1.get(variableName);
                    String sum = Double.toString(list.stream().mapToDouble(v -> v).sum());
                    line = line.replace(group, sum);
                } else if (repository2.containsKey(variableName)) {
                    Set<Double> list = (Set<Double>) repository2.get(variableName);
                    String sum = Double.toString(list.stream().mapToDouble(v -> v).sum());
                    line = line.replace(group, sum);
                }
            }
        }
        return line;
    }
}