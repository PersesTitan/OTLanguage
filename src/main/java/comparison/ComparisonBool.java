package comparison;

import item.work.ComparisonWork;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//ㅇㅇ ㄸ ㄴㄴ ㅇㄴ 와 같은 연산자 변환
public class ComparisonBool implements ComparisonWork {
    private final String notPattern = "ㅇㄴ\\s*(ㅇㅇ|ㄴㄴ)";
    private final String PATTERN = "(ㅇㅇ|ㄴㄴ)\\s*[ㄸㄲ]\\s*(ㅇㅇ|ㄴㄴ)";
    private final String bool = "ㅇㅇ|ㄴㄴ";
    private final String sing = "[ㄸㄲ]";
    private final Pattern not = Pattern.compile(notPattern);
    private final Pattern pattern = Pattern.compile(PATTERN);
    private final Pattern boolPattern = Pattern.compile(bool);
    private final Pattern singPattern = Pattern.compile(sing);
    private final static char left = '(';
    private final static char right = ')';

    @Override
    public String start(String line) throws Exception {
        return stack(line);
    }

    private String stack(String line) throws Exception {
        Stack<Integer> stack = new Stack<>();
        line = notChange(line);
        for (int i = 0; i<line.length(); i++) {
            if (line.charAt(i) == left) stack.add(i);
            else if (line.charAt(i) == right) {
                if (stack.isEmpty()) throw new Exception("괄호 짝이 일치하지 않습니다.");
                int start = stack.pop();
                String bool = line.substring(start, i+1);
                line = line.replaceFirst(bool, comparison(line));
                i = start;
            }
        }
        return line;
    }

    // ㅇㅇ ㄸ ㅇㅇ 를 따로 분리시키는 작업
    private String comparison(String line) {
        line = notChange(line);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String value = matcher.group();
            Matcher singMatch = this.singPattern.matcher(value);
            Matcher boolMatch = this.boolPattern.matcher(value);
            String sing = singMatch.group();
            String bool1 = boolMatch.group(0);
            String bool2 = boolMatch.group(1);
            line = line.replaceFirst(value, check(bool1, bool2, sing));
            notChange(line);
        }
        return line;
    }

    //ㅇㅇ ㄴㄴ ㄸ ㄲ 를 계산하고 반환하는 작업
    private String check(String b1, String b2, String sing) {
        boolean bool1 = b1.equals("ㅇㅇ");
        boolean bool2 = b2.equals("ㅇㅇ");
        if (sing.equals("ㄸ")) return bool1 || bool2 ? "ㅇㅇ" : "ㄴㄴ";
        else return bool1 && bool2 ? "ㅇㅇ" : "ㄴㄴ";
    }

    //ㅇㄴ(ㅇㅇ|ㄴㄴ) ㅇㄴㅇㅇ, ㅇㄴㄴㄴ 변경
    private String notChange(String line) {
        Matcher matcher = not.matcher(line);
        while (matcher.find()) {
            String value = matcher.group();
            if (value.endsWith("ㅇㅇ")) line = line.replaceFirst(value, "ㄴㄴ");
            else line = line.replaceFirst(value, "ㅇㅇ");
        }
        return line;
    }

    @Override
    public boolean check(String line) throws Exception {
        return not.matcher(line).find() || pattern.matcher(line).find();
    }
}
