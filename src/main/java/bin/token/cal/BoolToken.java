package bin.token.cal;

import static bin.token.Token.TOKEN;

public interface BoolToken {
    // BOOLEAN
    String TRUE = "ㅇㅇ";
    String FALSE = "ㄴㄴ";
    String OR = "ㄸ";
    String AND = "ㄲ";
    String NOT = "ㅇㄴ";

    String BOOL = "(" + TRUE + "|" + FALSE + ")";

    String BIG = TOKEN + ">" + TOKEN;
    String SMALL = TOKEN + "<" + TOKEN;
    String SAME = TOKEN + "=" + TOKEN;
    String BIG_SAME = TOKEN + ">=" + TOKEN;
    String SMALL_SAME = TOKEN + "<=" + TOKEN;
}