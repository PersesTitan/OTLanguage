package custom.tool;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomGetVariable {
    private final String countPattern = "^~*";
    private final String patternText = ":~*[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9_-]+[ _]";
    private final Pattern pattern = Pattern.compile(patternText);

    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    public String start(String line,
                        Map<String, Map<String, Object>> repository1,
                        Map<String, Map<String, Object>> repository2,
                        Map<String, Map<String, Object>> repository3) {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String group = matcher.group(); //:~변수명_
            int count = getCount(group);//~ 갯수
            String variableName = getVariableName(group); //변수명
            var repository = switch (count) {
                case 0 -> repository1;
                case 1 -> repository2;
                default -> repository3;
            };
            line = getVariable(line, variableName, repository);
        }
        return line;
    }

    //클래스 레벨 저장소
    public String start(String line,
                      Map<String, Map<String, Object>> repository1,
                      Map<String, Map<String, Object>> repository2) {
        Matcher matcher = pattern.matcher(line); //:~~변수명_
        while (matcher.find()) {
            String group = matcher.group();
            int count = getCount(group); //~ 갯수
            String variableName = getVariableName(group); //변수명
            var repository = count==0 ? repository1 : repository2;
            line = getVariable(line, variableName, repository);
        }
        return line;
    }

    // ~ 개수 구하기 // :~~변수명_
    private int getCount(String group) {
        group = group.substring(1, group.length()-1); //:~~변수명_ => ~~변수명
        Matcher countMatcher = Pattern.compile(countPattern).matcher(group);
        if (countMatcher.find()) return countMatcher.group().length(); //~~ 갯수 반환
        return 0;
    }

    // 변수명 구하기 // :~~변수명_
    private String getVariableName(String group) {
        group = group.substring(1, group.length()-1); //:~~변수명_ => ~~변수명
        return group.replaceFirst(countPattern, ""); // ~~변수명 => 변수명
    }

    // line : 변경 라인
    // key : 변수명
    private String getVariable(String line, String key,
                               Map<String, Map<String, Object>> repository) {
        for (String keys : repository.keySet()) {
            if (repository.get(keys).containsKey(key))
                line = line.replaceFirst(patternText, repository.get(keys).get(key).toString());
        }
        return line;
    }
}
