package math.controller;

import math.model.CalculationWork;
import origin.exception.MatchException;
import origin.exception.MatchMessage;

import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BoolCalculation implements CalculationWork {
    private final String notPattern = "ㅇㄴ\\s*(ㅇㅇ|ㄴㄴ)";
    private final String PATTERN = "(ㅇㅇ|ㄴㄴ)\\s*[ㄸㄲ]\\s*(ㅇㅇ|ㄴㄴ)";
    private final Pattern not = Pattern.compile(notPattern);
    private final Pattern pattern = Pattern.compile(PATTERN);
    private final static char left = '(';
    private final static char right = ')';

    @Override
    public String start(String line) {
        return checkRun(line);
    }

    @Override
    public boolean check(String line) {
        return not.matcher(line).find() || pattern.matcher(line).find();
    }

    //괄호 있는지 확인 하고 계산
    private String checkRun(String line) {
        line = notChange(line);
        if (line.contains("(") || line.contains(")")) {
            line = stack(line);
            line = notChange(line);
        }
        return cut(line);
    }

    //ㅇㅇ ㄸ ㄴㄴ => ㅇㅇ
    private String cut(String line) {
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
                throw new MatchException(MatchMessage.boolError);
            }
        }
        return line;
    }

    //====================================================================================
    private String stack(String line) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i<line.length(); i++) {
            if (line.charAt(i) == left) stack.add(i);
            else if (line.charAt(i) == right) {
                if (stack.isEmpty()) throw new MatchException(MatchMessage.matchError);
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
}
