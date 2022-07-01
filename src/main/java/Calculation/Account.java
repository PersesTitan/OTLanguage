package Calculation;

import item.work.Check;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account implements Check {
    //숫자 기호 숫자
    private final String patternText = "-?\\d+\\.?\\d*\\s*(ㅇ\\+ㅇ|ㅇ-ㅇ|ㅇ\\*ㅇ|ㅇ/ㅇ|ㅇ%ㅇ)\\s*-?\\d+\\.?\\d*";
    private final String singText = "ㅇ\\+ㅇ|ㅇ-ㅇ|ㅇ\\*ㅇ|ㅇ/ㅇ|ㅇ%ㅇ";
    private final String numberText = "-?\\d+\\.?\\d*";
    private final Pattern pattern = Pattern.compile(patternText);
    private final Pattern singPattern = Pattern.compile(singText);
    private final Pattern numberPattern = Pattern.compile(numberText);
    private static final char left = '(';
    private static final char right = ')';

    //숫자 기호 숫자가 없어질때까지 반복
    public String account(String line) throws Exception {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String value = matcher.group();
            Matcher singMatcher = singPattern.matcher(value);
            if (singMatcher.find()) {
                String sing = singMatcher.group();
                StringTokenizer stringTokenizer = new StringTokenizer(value, sing);
                String number1 = stringTokenizer.nextToken().strip();
                String number2 = stringTokenizer.nextToken().strip();
                line = line.replace(value, check(number1, number2, sing));
            } else throw new Exception("문법 오류");
        }
        return line;
    }

    //소수인지 정수인지 확인하고 값을 계산하는 식
    public String check(String num1, String num2, String sing) {
        if (num1.contains(".") || num2.contains(".")) {
            double number1 = Double.parseDouble(num1);
            double number2 = Double.parseDouble(num2);
            switch (sing) {
                case "ㅇ+ㅇ" : return String.valueOf(number1 + number2);
                case "ㅇ-ㅇ" : return String.valueOf(number1 - number2);
                case "ㅇ*ㅇ" : return String.valueOf(number1 * number2);
                case "ㅇ/ㅇ" : return String.valueOf(number1 / number2);
                case "ㅇ%ㅇ" : return String.valueOf(number1 % number2);
                default: assert false;
            }
        } else {
            long number1 = Long.parseLong(num1);
            long number2 = Long.parseLong(num2);
            switch (sing) {
                case "ㅇ+ㅇ" : return String.valueOf(number1 + number2);
                case "ㅇ-ㅇ" : return String.valueOf(number1 - number2);
                case "ㅇ*ㅇ" : return String.valueOf(number1 * number2);
                case "ㅇ/ㅇ" : return String.valueOf(number1 / number2);
                case "ㅇ%ㅇ" : return String.valueOf(number1 % number2);
                default: assert false;
            }
        }
        return "0";
    }

    private String stack(String line) throws Exception {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i<line.length(); i++) {
            if (line.charAt(i) == left) {
                stack.add(i);
            } else if (line.charAt(i) == right) {
                if (stack.isEmpty()) throw new Exception("괄호의 짝이 맞지 않습니다.");
                int start = stack.pop();
                //(숫자 ㅇ+ㅇ 숫자)
                String numbers = line.substring(start, i+1);
                line = line.replaceFirst(numbers, account(numbers));
                i = start;
            }
        }

        return account(line);
    }

    @Override
    public boolean check(String line) throws Exception {
        return pattern.matcher(line).find();
    }

    public String start(String line) throws Exception {
        return stack(line);
    }
}
