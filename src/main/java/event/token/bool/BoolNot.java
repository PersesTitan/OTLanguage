package event.token.bool;

import event.token.Token;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static event.token.BoolCalculator.*;
import static event.token.Token.makeBlank;

public interface BoolNot extends Token {
    String patternText = makeBlank(NOT, trueFalse);
    Pattern pattern = Pattern.compile(patternText);

    static String getNot(String line) {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String group = matcher.group();
            group = deletePattern(group, NOT);
            line = line.replaceFirst(patternText, group.equals("ㅇㅇ") ? "ㄴㄴ" : "ㅇㅇ");
        }
        return line;
    }

    static boolean check(String line) {
        return pattern.matcher(line).find();
    }
}
