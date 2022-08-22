package event.token.bool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static event.token.BoolCalculator.*;
import static event.token.Token.*;

public interface BoolAndOr {
    String orPattern = orPattern(AND, OR);
    String patternText = makeBlank(trueFalse, orPattern, trueFalse);
    Pattern pattern = Pattern.compile(patternText);

    static String getAnd(String line) {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String group = matcher.group();
            String token1 = getPattern(group, trueFalse);
            group = deletePattern(group, trueFalse);
            String token2 = getPattern(group, orPattern);
            group = deletePattern(group, orPattern);
            String token3 = getPattern(group, trueFalse);
            boolean bool1 = token1.equals("ㅇㅇ");
            boolean bool2 = token3.equals("ㅇㅇ");
            line = line.replaceFirst(
                    patternText,
                    token2.equals(AND)
                            ? (bool1 && bool2 ? "ㅇㅇ" : "ㄴㄴ")
                            : (bool1 || bool2 ? "ㅇㅇ" : "ㄴㄴ"));
        }
        return line;
    }

    static boolean check(String line) {
        return pattern.matcher(line).find();
    }
}
