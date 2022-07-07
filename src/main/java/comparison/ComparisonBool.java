package comparison;

import item.work.ComparisonWork;

import java.util.Stack;
import java.util.StringTokenizer;
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
        return checkRun(line);
    }

    //괄호 있는지 확인 하고 계산
    private String checkRun(String line) throws Exception {
        line = notChange(line);
        if (line.contains("(") || line.contains(")")) {
            line = stack(line);
            line = notChange(line);
        }
        return cut(line);
    }


    //ㅇㅇ ㄸ ㄴㄴ => ㅇㅇ
    private String cut(String line) throws Exception {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String value = matcher.group();
            StringTokenizer tokenizer = new StringTokenizer(value, "ㄸㄲ", true);
            try {
                String text1 = tokenizer.nextToken().strip();
                String sing = tokenizer.nextToken();
                String text2 = tokenizer.nextToken().strip();
                boolean b1 = text1.equals("ㅇㅇ");
                boolean b2 = text2.equals("ㅇㅇ");
                boolean b = sing.equals("ㄸ") ? (b1 || b2) : (b1 && b2);
                line = line.replaceFirst(value, b ? "ㅇㅇ" : "ㄴㄴ");
            } catch (Exception ignored) {
                throw new Exception("문법 오류 발생");
            }
        }
        return line;
    }

    //====================================================================================
    private String stack(String line) throws Exception {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i<line.length(); i++) {
            if (line.charAt(i) == left) stack.add(i);
            else if (line.charAt(i) == right) {
                if (stack.isEmpty()) throw new Exception("괄호 짝이 일치하지 않습니다.");
                int start = stack.pop();
                String bool = line.substring(start, i+1);
                line = line.replace(bool, cut(bool.substring(1, bool.length()-1)));
                i = start;
            }
        }
        return line;
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
