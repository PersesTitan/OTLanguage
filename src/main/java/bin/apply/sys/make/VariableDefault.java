package bin.apply.sys.make;

import bin.apply.Setting;
import bin.token.LoopToken;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableDefault implements LoopToken {
    private final String defaultValue = ";";

    private final String VALUES_ALL = "[^" + VARIABLE_GET_E + VARIABLE_GET_S + defaultValue + "]+";
    private final String VALUES_ONE = "[^" + VARIABLE_GET_E + VARIABLE_GET_S + defaultValue + "]*";

    private final Matcher variableDefaultMatch =
            Pattern.compile(
            VARIABLE_GET_S + VALUES_ALL + VARIABLE_GET_E + "(" + VALUES_ONE + defaultValue + ")?").matcher("");
    private final Matcher variableDefaultMatcherCheck =
        Pattern.compile(
        VARIABLE_GET_S + VALUES_ALL +
        VARIABLE_GET_E + VALUES_ONE + defaultValue).matcher("");

    public boolean check(String line) {
        map.clear();
        return variableDefaultMatcherCheck.reset(line).find();
    }

    // 변수명, 몇째인지
    private final Map<String, Map<Integer, String>> map = new HashMap<>();
    public String start(String line) {
        variableDefaultMatch.reset(line);
        while (variableDefaultMatch.find()) {
            String[] group = bothEndCut(variableDefaultMatch.group()).split(VARIABLE_GET_E, 2);
            if (map.containsKey(group[0]))
                map.get(group[0]).put(map.get(group[0]).size()+1, group.length == 1 ? null : group[1]);
            else map.put(group[0], new HashMap<>(){{put(1, group.length == 1 ? null : group[1]);}});
        }
        return line.replaceAll(
                "(?<=" + VARIABLE_GET_S + VALUES_ALL + VARIABLE_GET_E + ")" +
                       VALUES_ONE + defaultValue, "");
    }

    private final Matcher changeMatcher = Pattern.compile(VARIABLE_GET_S + VALUES_ALL + VARIABLE_GET_E).matcher("");
    public boolean changeCheck(String line) {
        return !map.isEmpty() && changeMatcher.reset(line).find();
    }

    public String changeStart(String line) {
        changeMatcher.reset();
        Set<String> set = new HashSet<>();
        while (changeMatcher.find()) set.add(bothEndCut(changeMatcher.group()));
        for (String s : set) {
            String[] variables = line.split(Pattern.quote(VARIABLE_GET_S + s + VARIABLE_GET_E));
            if (variables.length == 0) return VARIABLE_GET_S + s + VARIABLE_GET_E;
            StringBuilder builder = new StringBuilder(variables[0]);
            for (int i = 1; i<variables.length; i++) {
                var v = map.get(s).get(i);
                builder.append(v == null ? VARIABLE_GET_S + s + VARIABLE_GET_E : v).append(variables[i]);
            }
            if (map.get(s).containsKey(variables.length)) {
                var v = map.get(s).get(variables.length);
                builder.append(v == null ? VARIABLE_GET_S + s + VARIABLE_GET_E : v);
            }
            line = builder.toString();
        }
        return line;
    }
}
