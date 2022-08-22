package event.token;

import event.token.bool.BoolAndOr;
import event.token.bool.BoolCompare;
import event.token.bool.BoolNot;
import origin.exception.MatchException;
import origin.exception.MatchMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static event.token.NumberCalculator.calculations;
import static event.token.Token.*;

public class BoolCalculator implements Token {
    public final static String trueFalse = orPattern(TRUE, FALSE);
    // 숫자 (ㅇ>ㅇ|ㅇ<ㅇ|ㅇ=ㅇ|ㅇ>=ㅇ|ㅇ<=ㅇ) 숫자
    private final static String number = orPattern(calculations, NUMBER);
    private final static String compare = makeBlank(number, orPattern(BIG, SMALL, SAME, BIG_SAME, SMALL_SAME), number);
    private final static String orAnd = orPattern(OR, AND);  // (ㄸ|ㄲ)
    // (ㅇㄴ\\s*)?(숫자(ㅇ>ㅇ|ㅇ<ㅇ|ㅇ=ㅇ|ㅇ>=ㅇ|ㅇ<=ㅇ)숫자|ㅇㅇ|ㄴㄴ)
    private final static String not = make("(", NOT, "\\s*)?", orPattern(compare, TRUE, FALSE, VARIABLE));
    private final static String bool = makeBlank(not, "(", orAnd, not, ")*");
    private final static String first = makeBlank(SL, bool, SR); // (ㅇㅇ)
    private final static String bools = orPattern(first, bool);

    public static String getBool(String line) {
        System.out.println(bools);
        line = setCompare(line);
        Matcher matcher = Pattern.compile(bools).matcher(line);
        while (matcher.find()) {
            while (line.contains("(") && line.contains(")")) {
                Matcher matcher1 = Pattern.compile(first).matcher(line);
                while (matcher1.find()) line = line.replaceFirst(first, setBlank(matcher1.group()));
            }
            Matcher matcher2 = Pattern.compile(bool).matcher(line);
            while (matcher2.find()) line = line.replaceFirst(bool, setBlank(matcher2.group()));
        }
        return line;
    }

    private static String setBlank(String group) {
        group = deleteBlank(group); // 괄호 삭제
        Matcher matcher = Pattern.compile(bool).matcher(group);
        while (matcher.find()) {
            if (BoolNot.check(group)) group = group.replaceFirst(bool, BoolNot.getNot(group));
            if (BoolAndOr.check(group)) group = group.replaceFirst(bool, BoolAndOr.getAnd(group));
        }
        return replacePattern(group);
    }

    private static String setCompare(String line) {
        Matcher matcher = Pattern.compile(compare).matcher(line);
        while (matcher.find()) {
            String group = matcher.group();
            if (BoolCompare.check(group))
                line = line.replaceFirst(compare, BoolCompare.getCompare(group));
        }
        return line;
    }

    static String deleteBlank(String line) {
        return line.startsWith("(")
                ? line.substring(1, line.length()-1)
                : line;
    }

    public static String deletePattern(String group, String pattern) {
        return group.replaceFirst("^\\s*" + pattern + "\\s*", "");
    }

    public static String getPattern(String group, String pattern) {
        Matcher matcher = Pattern.compile(pattern).matcher(group);
        if (matcher.find()) return matcher.group();
        throw new MatchException(MatchMessage.grammarError);
    }
}
