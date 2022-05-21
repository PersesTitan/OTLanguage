package Variable;

import item.Check;
import item.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Variable extends Setting implements Check {
    // :[한국어 or 영어 or 숫자][공백] 형태
    private final String text = ":([ㄱ-ㅎㅏ-ㅣ가-힣]|\\w)\\b";
    private final Pattern pattern = Pattern.compile(text);

    /**
     * 변수 :[변수명][공백] 을 변수 값으로 대체함
     * @param line 한줄을 받아옴
     */
    public List<String> getVar(String line) {
        List<String> list = new ArrayList<>();
        String[] words = line.split(" ");
        for (String word : words) {
            if (!word.isBlank() && word.contains(":")) {
                int start = word.indexOf(":") + 1;
                list.add(word.substring(start));
            }
        }
        return list;
    }

    /**
     * 공백 단위로 쪼갠 뒤
     * @param line 첫 번째 줄 받아오기
     * @return 변수가 존재시 값을 받아옴
     */
    @Override
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