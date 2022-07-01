package Variable;

import item.Setting;
import item.kind.VarType;

import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Variable extends Setting {
    // :[한국어 or 영어 or 숫자][공백] 형태
    private final String text = ":\\S+ ";
    private final Pattern pattern = Pattern.compile(text);

    /**
     * 변수 :[변수명][공백] 을 변수 값으로 대체함
     * @param line 한줄을 받아옴
     */
    public String getVar(String line) {
        Matcher m = pattern.matcher(line);
        while(m.find()) {
            String origin = m.group();
            String key = origin.strip().substring(1);
            if (set.contains(key))
                line = line.replaceFirst(origin, checkValue(key));
        }
        return line;
    }

    /**
     * 공백 단위로 쪼갠 뒤
     * @param line 첫 번째 줄 받아오기
     * @return 변수가 존재시 값을 받아옴
     */
    public boolean check(String line) {
        if (line == null || line.isEmpty()) return false;
        boolean bool = pattern.matcher(line).find();

        var lines = line.split(" ");
        return bool || Arrays.stream(lines)
                .filter(v -> !v.isEmpty())
                .filter(v -> v.startsWith(":"))
                .anyMatch(v -> set.contains(v.substring(1)));
    }
}