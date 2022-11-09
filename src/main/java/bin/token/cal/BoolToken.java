package bin.token.cal;

import static bin.token.Token.NO_TOKEN;
import static bin.token.Token.TOKEN;

public interface BoolToken {
    // BOOLEAN
    String TRUE = TOKEN.repeat(2);
    String FALSE = NO_TOKEN.repeat(2);
    String OR = "ㄸ";
    String AND = "ㄲ";
    String NOT = TOKEN + NO_TOKEN;

    String BOOL = "(" + TRUE + "|" + FALSE + ")";

    String BIG = TOKEN + ">" + TOKEN;
    String SMALL = TOKEN + "<" + TOKEN;
    String SAME = TOKEN + "=" + TOKEN;
    String BIG_SAME = TOKEN + ">=" + TOKEN;
    String SMALL_SAME = TOKEN + "<=" + TOKEN;
}