package event.token.number;

import event.token.Token;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static event.token.BoolCalculator.deletePattern;
import static event.token.BoolCalculator.getPattern;
import static event.token.Token.makeBlank;
import static event.token.Token.orPattern;

public interface NumberCal extends Token {
    String sing = orPattern(PLUS_CALCULATOR, MINUS_CALCULATOR, DIVIDE_CALCULATOR, MULTIPLY_CALCULATOR, REMAINDER_CALCULATOR);
    String patternText = makeBlank(NUMBER, sing, NUMBER);
    Pattern pattern = Pattern.compile(patternText);

    String PLUS = "ㅇ+ㅇ";
    String MINUS = "ㅇ-ㅇ";
    String DIVIDE = "ㅇ/ㅇ";
    String MULTIPLY = "ㅇ*ㅇ";
    String REMAINDER = "ㅇ%ㅇ";

    static String start(String line) {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String group = matcher.group();
            String token1 = getPattern(group, NUMBER);
            group = deletePattern(group, NUMBER);
            String token2 = getPattern(group, sing);
            group = deletePattern(group, sing);
            String token3 = getPattern(group, NUMBER);
            if (Pattern.compile(FLOAT).matcher(token1).find())
                line = line.replaceFirst(patternText,
                        cal(Double.parseDouble(token1), Double.parseDouble(token3), token2) + "");
            else
                line = line.replaceFirst(patternText,
                        cal(Integer.parseInt(token1), Integer.parseInt(token3), token2) + "");
        }
        return line;
    }

    static boolean check(String line) {
        return pattern.matcher(line).find();
    }

    static int cal(int num1, int num2, String token) {
        return switch (token) {
            case PLUS -> num1 + num2;
            case MINUS -> num1 - num2;
            case DIVIDE -> num1 / num2;
            case MULTIPLY -> num1 * num2;
            case REMAINDER -> num1 % num2;
            default -> 0;
        };
    }

    static double cal(double num1, double num2, String token) {
        return switch (token) {
            case PLUS -> num1 + num2;
            case MINUS -> num1 - num2;
            case DIVIDE -> num1 / num2;
            case MULTIPLY -> num1 * num2;
            case REMAINDER -> num1 % num2;
            default -> 0.0;
        };
    }

    static String delete(String line) {
        return line.replace("\\", "");
    }
}
