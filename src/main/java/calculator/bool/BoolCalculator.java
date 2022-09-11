package calculator.bool;

import apply.Controller;
import token.MergeToken;
import token.Token;
import token.cal.BoolToken;
import token.cal.NumberToken;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BoolCalculator implements
        BoolToken, NumberToken, MergeToken, Token {
    private final String sings = orMerge(AND, OR); // (ㄸ|ㄲ)
    private final String bool = orMerge(TRUE, FALSE);
    private final String bools =    // (ㅇㅇㄸㅇㅇ)
            blackMerge(SL, orMerge(blackMerge(bool, sings, bool, "(", sings, bool, ")*"), bool), SR);

    public String start(String line) {
        line = Controller.compareCalculator.start(line);
        return blank(line);
    }

    private String blank(String line) {
        Matcher matcher = Pattern.compile(this.bools).matcher(line);
        while (matcher.find()) {
            String group = matcher.group(); // (계산식)
            String bools = group.substring(1, group.length()-1); // 계산식
            line = line.replaceFirst(this.bools, calculator(bools));
            matcher.reset(line);
        }
        return calculator(not(line));
    }

    private String calculator(String line) {
        String patternText = blackMerge(this.bool, this.sings, this.bool);
        Matcher matcher = Pattern.compile(patternText).matcher(line);
        while (matcher.find()) {
            String group = matcher.group().replaceAll(BLANKS, "");
            String[] or = group.split(OR);
            String[] and = group.split(AND);
            if (or.length == 2)
                line = line.replaceFirst(patternText, calculator(or[0], or[1], OR));
            else if (and.length == 2)
                line = line.replaceFirst(patternText, calculator(and[0], and[1], AND));
            matcher.reset(line);
        }
        return line;
    }

    private String not(String line) {
        return line
                .replaceAll(blackMerge(NOT, TRUE), FALSE)
                .replaceAll(blackMerge(NOT, FALSE), TRUE);
    }

    private String calculator(String bool1, String bool2, String sing) {
        boolean b1 = bool1.equals(TRUE);
        boolean b2 = bool2.equals(TRUE);
        if (sing.equals(OR)) return b1 || b2 ? TRUE : FALSE;
        else return b1 && b2 ? TRUE : FALSE;
    }
}
