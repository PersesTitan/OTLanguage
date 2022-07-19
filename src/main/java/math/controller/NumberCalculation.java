package math.controller;

import math.model.CalculationWork;
import origin.exception.MatchException;
import origin.exception.MatchMessage;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberCalculation implements CalculationWork {

    private final String PATTERN = "-?\\d+\\.?\\d*\\s*(ㅇ<ㅇ|ㅇ>ㅇ|ㅇ=ㅇ|ㅇ<=ㅇ|ㅇ>=ㅇ)\\s*-?\\d+\\.?\\d*";
    private final String singText = "ㅇ<ㅇ|ㅇ>ㅇ|ㅇ=ㅇ|ㅇ<=ㅇ|ㅇ>=ㅇ";
    private final String numberText = "-?\\d+\\.?\\d*";
    private final Pattern pattern = Pattern.compile(PATTERN);
    private final Pattern singPattern = Pattern.compile(singText);
    private final Pattern numberPattern = Pattern.compile(numberText);
    private final static char left = '(';
    private final static char right = ')';

    @Override
    public String start(String line) {
        return stack(line);
    }

    //숫자 기호 숫자 형태의 모양이 없어질때까지 라인 스캔
    private String comparison(String line) {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String value = matcher.group();
            Matcher singMatcher = singPattern.matcher(value);
            Matcher numberMatcher = numberPattern.matcher(value);
            String number1 = numberMatcher.group(0);
            String number2 = numberMatcher.group(1);
            String sing = singMatcher.group();
            line = line.replaceFirst(value, check(number1, number2, sing)?"ㅇㅇ":"ㄴㄴ");
        }
        return line;
    }

    private boolean check(String num1, String num2, String sing) {
        double number1 = Double.parseDouble(num1);
        double number2 = Double.parseDouble(num2);
        return switch (sing) {
            case "ㅇ>ㅇ" -> number1 > number2;
            case "ㅇ<ㅇ" -> number1 < number2;
            case "ㅇ=ㅇ" -> number1 == number2;
            case "ㅇ>=ㅇ" -> number1 >= number2;
            case "ㅇ<=ㅇ" -> number1 <= number2;
            default -> throw new MatchException(MatchMessage.grammarError);
        };
    }

    //괄호가 있으면 괄호가 있으면 괄호를 먼저 계산함
    private String stack(String line) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i<line.length(); i++) {
            if (line.charAt(i) == left) stack.add(i);
            else if (line.charAt(i) == right) {
                if (stack.isEmpty()) throw new MatchException(MatchMessage.matchError);
                int start = stack.pop();
                //(10ㅇ>ㅇ25)
                String numbers = line.substring(start, i+1);
                line = line.replaceFirst(numbers, comparison(line));
                i = start;
            }
        }
        return comparison(line);
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }
}
