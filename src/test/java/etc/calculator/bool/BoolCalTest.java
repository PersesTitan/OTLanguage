package etc.calculator.bool;

import bin.exception.MatchException;
import bin.token.Token;
import bin.token.cal.BoolToken;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BoolCalTest implements BoolToken, Token {
    private final String BOOL = orMerge(TRUE, FALSE);
    private final String orAnd = orMerge(AND, OR);
    private final String orAndPatternText = BOOL + BLANK + orAnd + BLANK + BOOL;

    private final Matcher matcherOrAnd = Pattern.compile(orAndPatternText).matcher("");
    public String orAnd(String line) {
        line = not(line);
        while (matcherOrAnd.reset(line).find()) {
            StringTokenizer tokenizer = new StringTokenizer(matcherOrAnd.group(), OR + AND, true);
            boolean bool1 = tokenizer.nextToken().strip().equals(TRUE);
            String sing = tokenizer.nextToken();
            boolean bool2 = tokenizer.nextToken().strip().equals(TRUE);
            boolean bool = sing.equals(OR) ? (bool1 || bool2) : (bool1 && bool2);
            line = line.replaceFirst(orAndPatternText, bool ? TRUE : FALSE);
        }
        return matcherOrAnd.reset((line = blank(line))).find() ? orAnd(line) : line;
    }

    private final Matcher matcherNo = Pattern.compile(NOT + BLANK + BOOL).matcher("");
    private String not(String value) {
        return matcherNo.reset(value).find()
                ? value
                    .replaceAll(NOT + BLANK + TRUE, FALSE)
                    .replaceAll(NOT + BLANK + FALSE, TRUE)
                : value;
    }

    private final Matcher matcherBlank = Pattern.compile(SL + BLANK + BOOL + BLANK + SR).matcher("");
    private String blank(String value) {
        return matcherBlank.reset(value).find()
                ? value
                    .replaceAll(SL + BLANK + TRUE + BLANK + SR, TRUE)
                    .replaceAll(SL + BLANK + FALSE + BLANK + SR, FALSE)
                : value;
    }
}
