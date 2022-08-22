package event.token.bool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static event.token.BoolCalculator.*;
import static event.token.Token.*;

public interface BoolCompare {
    String orPattern = orPattern(BIG, SMALL, SAME, BIG_SAME, SMALL_SAME);
    String patternText = makeBlank(NUMBER, orPattern, NUMBER);
    Pattern pattern = Pattern.compile(patternText);

    static String getCompare(String line) {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String group = matcher.group();
            double token1 = Double.parseDouble(getPattern(group, NUMBER));
            group = deletePattern(group, NUMBER);
            String token2 = getPattern(group, orPattern);
            group = deletePattern(group, orPattern);
            double token3 = Double.parseDouble(getPattern(group, NUMBER));
            line = line.replaceFirst(patternText,
                    switch (token2) {
                        case BIG -> token1 > token3;
                        case SMALL -> token1 < token3;
                        case SAME -> token1 == token3;
                        case BIG_SAME -> token1 >= token3;
                        case SMALL_SAME -> token1 <= token3;
                        default -> false;
                    } ? "ㅇㅇ" : "ㄴㄴ");
        }
        return line;
    }

    static boolean check(String line) {
        return pattern.matcher(line).find();
    }
}
