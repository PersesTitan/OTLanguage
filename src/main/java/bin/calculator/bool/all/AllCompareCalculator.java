package bin.calculator.bool.all;

import bin.apply.Controller;
import bin.token.MergeToken;
import bin.token.Token;
import bin.token.cal.BoolToken;
import bin.token.cal.NumberToken;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AllCompareCalculator implements
        BoolToken, NumberToken, MergeToken, Token {
    private final String sings = orMerge(BIG, SMALL, SAME, BIG_SAME, SMALL_SAME);
    private final String sing = blackMerge(NUMBER, this.sings, NUMBER);
    private final Matcher singPattern = Pattern.compile(sing).matcher("");

    public String start(String line) {
        line = Controller.allNumberCalculator.start(line);
        return blank(line);
    }

    private final Matcher matcher = Pattern.compile(sing).matcher("");
    private String blank(String line) {
        String sing = blackMerge(SL, NUMBER, this.sings, NUMBER, BL);
        matcher.reset(line);
        while (matcher.find()) {
            String group = matcher.group().replaceFirst(BLANKS, "");
            String var = group.substring(1, group.length()-1);
            line = line.replaceFirst(sing, calculator(var));
        }
        return calculator(line);
    }

    private String calculator(String line) {
        singPattern.reset(line);
        while (singPattern.find()) {
            String group = singPattern.group();
            String[] big = group.split(BIG, 2);
            if (big.length == 2) {line = line.replaceFirst(sing, calculator(big, BIG));continue;}
            String[] small = group.split(SMALL, 2);
            if (small.length == 2) {line = line.replaceFirst(sing, calculator(small, SMALL)); continue;}
            String[] same = group.split(SAME, 2);
            if (same.length == 2) {line = line.replaceFirst(sing, calculator(same, SAME)); continue;}
            String[] bigSame = group.split(BIG_SAME, 2);
            if (bigSame.length == 2) {line = line.replaceFirst(sing, calculator(bigSame, BIG_SAME)); continue;}
            String[] smallSame = group.split(SMALL_SAME, 2);
            if (smallSame.length == 2) line = line.replaceFirst(sing, calculator(smallSame, SMALL_SAME));
        }
        return line;
    }

    private String calculator(String[] nums, String sing) {
        double n1 = Double.parseDouble(nums[0]);
        double n2 = Double.parseDouble(nums[1]);
        return switch (sing) {
            case BIG -> n1 > n2 ? TRUE : FALSE;
            case SMALL -> n1 < n2 ? TRUE : FALSE;
            case SAME -> n1 == n2 ? TRUE : FALSE;
            case BIG_SAME -> n1 >= n2 ? TRUE : FALSE;
            default -> n1 <= n2 ? TRUE : FALSE;
        };
    }
}
