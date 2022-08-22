package custom.tool;

import event.Controller;
import event.Setting;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomSetVariable {
    private final String countPattern = "^~*";
    private final String patternText = "^\\s*~*"+ Setting.variableStyle+":";
    private final Pattern pattern = Pattern.compile(patternText);

    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    public void start(String line,
                      Map<String, Map<String, Object>> repository1,
                      Map<String, Map<String, Object>> repository2,
                      Map<String, Map<String, Object>> repository3,
                      Set<String> set1,
                      Set<String> set2,
                      Set<String> set3) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String group = matcher.group().strip(); //~~변수명:
            int count = getCount(group); //~ 갯수
            String variableName = getVariableName(line); //변수명:넣을 값
            var repository = switch (count) {
                case 0 -> repository1;
                case 1 -> repository2;
                default -> repository3;
            };
            var set = switch (count) {
                case 0 -> set1;
                case 1 -> set2;
                default -> set3;
            };
            Controller.setVariable.start(variableName, repository, set);
        }
    }

    public void start(String line,
                      Map<String, Map<String, Object>> repository1,
                      Map<String, Map<String, Object>> repository2,
                      Set<String> set1,
                      Set<String> set2) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String group = matcher.group().strip(); //~~변수명:
            int count = getCount(group); //~ 갯수
            String variableName = getVariableName(line); //변수명:넣을 값
            var repository = count==0 ? repository1 : repository2;
            var set = count==0 ? set1 : set2;
            Controller.setVariable.start(variableName, repository, set);
        }
    }

    // ~ 갯수 반환
    private int getCount(String group) {
        Matcher countMatcher = Pattern.compile(countPattern).matcher(group);
        if (countMatcher.find()) return countMatcher.group().length(); //~~ 갯수 반환
        return 0;
    }

    // ~~변수명:넣을 값 => 변수명:넣을값
    private String getVariableName(String group) {
        return group.replaceFirst(countPattern, "");
    }
}
