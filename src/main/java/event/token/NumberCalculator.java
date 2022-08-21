package event.token;

import event.token.number.NumberCal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static event.token.BoolCalculator.deleteBlank;
import static event.token.Token.*;
import static event.token.Token.makeBlank;

public class NumberCalculator implements Token {
    private final static String number = orPattern(NUMBER, VARIABLE);
    private final static String sing = orPattern(PLUS_CALCULATOR, MINUS_CALCULATOR, DIVIDE_CALCULATOR, MULTIPLY_CALCULATOR, REMAINDER_CALCULATOR);
    private final static String calculation = makeBlank(number, sing, number);
    public final static String calculations = makeBlank(calculation, "(", sing, number, ")*");
    private final static String first = makeBlank(SL, calculations, SR);
    private final static String numbers = orPattern(first, calculations);

    public static String getNumber(String line) {
        while (Pattern.compile(numbers).matcher(line).find()) {
            while (line.contains("(") && line.contains(")")) {
                Matcher matcher1 = Pattern.compile(first).matcher(line);
                while (matcher1.find()) line = line.replaceFirst(first, setBlank(matcher1.group()));
            }
            Matcher matcher2 = Pattern.compile(calculations).matcher(line);
            while (matcher2.find()) {
                line = line.replaceFirst(calculations, setBlank(matcher2.group()));
            }
        }

        return line;
    }

    private static String setBlank(String group) {
        group = deleteBlank(group); // 괄호 삭제
        Matcher matcher = Pattern.compile(calculations).matcher(group);
        while (matcher.find()) {
            if (NumberCal.check(group)) group = group.replaceFirst(calculations, NumberCal.start(group));
        }
        return replacePattern(group);
    }
}
