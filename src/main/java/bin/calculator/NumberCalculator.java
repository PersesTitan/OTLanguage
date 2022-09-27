package bin.calculator;

import bin.token.MergeToken;
import bin.token.cal.NumberToken;
import bin.token.Token;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bin.check.VariableCheck.*;

public class NumberCalculator implements
        Token, NumberToken, MergeToken {
    private final String PM =
            orMerge(PLUS_CALCULATOR, MINUS_CALCULATOR);
    private final String DM =
            orMerge(DIVIDE_CALCULATOR, MULTIPLY_CALCULATOR, REMAINDER_CALCULATOR);
    private final String patternText1 = blackMerge(NUMBER, DM, NUMBER);
    private final String patternText2 = blackMerge(NUMBER, PM, NUMBER);
    private final Pattern pattern1 = Pattern.compile(patternText1);
    private final Pattern pattern2 = Pattern.compile(patternText2);
    private final String sing = orMerge(PM, DM);  // (PM|DM)
    private final String number =
            blackMerge(SL, orMerge(NUMBER, blackMerge(NUMBER, sing, NUMBER, "(", sing, NUMBER, ")*")), SR);
    private final Pattern numberPattern = Pattern.compile(number);

    public String start(String line) {
        return blank(line);
    }

    private String blank(String line) {
        Matcher matcher = numberPattern.matcher(line); //괄호 확인하는 작업
        while (matcher.find()) {
            String group = matcher.group().replaceAll(BLANKS, ""); // (계산식)
            String numbers = group.substring(1, group.length()-1); // 계산식
            line = line.replaceFirst(number, calculator(numbers));
            matcher.reset(line);
        }
        return calculator(line);
    }

    private String calculator(String line) {
        Matcher matcher1 = pattern1.matcher(line);
        while (matcher1.find()) {
            String group = matcher1.group().replaceAll(BLANKS, ""); // 숫자 기호 숫자
            matcher1.reset(line);

            String[] multiply = group.split(MULTIPLY_CALCULATOR);
            if (multiply.length == 2) {
                line = line.replaceFirst(patternText1, calculator(multiply[0], multiply[1], MULTIPLY_CALCULATOR));continue;}
            String[] divide = group.split(DIVIDE_CALCULATOR);
            if (divide.length == 2) {
                line = line.replaceFirst(patternText1, calculator(divide[0], divide[1], DIVIDE_CALCULATOR));continue;}
            String[] remain = group.split(REMAINDER_CALCULATOR);
            if (remain.length == 2)
                line = line.replaceFirst(patternText1, calculator(remain[0], remain[1], REMAINDER_CALCULATOR));
        }

        Matcher matcher2 = pattern2.matcher(line);
        while (matcher2.find()) {
            String group = matcher2.group().replaceAll(BLANKS, ""); // 숫자 기호 숫자
            matcher2.reset(line);

            String[] plus = group.split(PLUS_CALCULATOR);
            if (plus.length == 2) {
                line = line.replaceFirst(patternText2, calculator(plus[0], plus[1], PLUS_CALCULATOR)); continue;}
            String[] minus = group.split(MINUS_CALCULATOR);
            if (minus.length == 2)
                line = line.replaceFirst(patternText2, calculator(minus[0], minus[1], MINUS_CALCULATOR));
        }
        return line;
    }

    private String calculator(String num1, String num2, String sing) {
        boolean isInt = isInt(num1, num2);
        double n1 = Double.parseDouble(num1);
        double n2 = Double.parseDouble(num2);
        return switch (sing) {
            case PLUS_CALCULATOR -> isInt ? Long.toString((int) (n1 + n2)) : Double.toString(n1 + n2);
            case MINUS_CALCULATOR -> isInt ? Long.toString((int) (n1 - n2)) : Double.toString(n1 - n2);
            case DIVIDE_CALCULATOR -> isInt ? Long.toString((int) (n1 / n2)) : Double.toString(n1 / n2);
            case MULTIPLY_CALCULATOR -> isInt ? Long.toString((int) (n1 * n2)) : Double.toString(n1 * n2);
            default -> isInt ? Long.toString((int) (n1 % n2)) : Double.toString(n1 % n2);
        };
    }

    private boolean isInt(String num1, String num2) {
        return isLong(num1) && isLong(num2);
    }
}
