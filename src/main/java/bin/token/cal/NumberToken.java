package bin.token.cal;

import static bin.token.Token.DOT;
import static bin.token.Token.TOKEN;

public interface NumberToken {
    // NUMBER
    String INT = "\\-?[0-9]+";             // -1234, 1234
    String FLOAT = INT + DOT + "[0-9]+(E[0-9]+)?";   // -1234.34, 1234.34
    String NUMBER = "(" + FLOAT + "|" + INT + ")";

    // OPERATOR
    String PLUS_CALCULATOR = TOKEN + "\\+" + TOKEN;
    String MINUS_CALCULATOR = TOKEN + "\\-" + TOKEN;
    String DIVIDE_CALCULATOR = TOKEN + "\\/" + TOKEN;
    String MULTIPLY_CALCULATOR = TOKEN + "\\*" + TOKEN;
    String REMAINDER_CALCULATOR = TOKEN + "\\%" + TOKEN;

    String PLUS_CALCULATOR_S = TOKEN + "+" + TOKEN;
    String MINUS_CALCULATOR_S = TOKEN + "-" + TOKEN;
    String DIVIDE_CALCULATOR_S = TOKEN + "/" + TOKEN;
    String MULTIPLY_CALCULATOR_S = TOKEN + "*" + TOKEN;
    String REMAINDER_CALCULATOR_S = TOKEN + "%" + TOKEN;

    // RANDOM
    String RANDOM_BOOL = "@ㅂ@";
    String RANDOM_DOUBLE = "@ㅆ@";
    String RANDOM_FLOAT = "@ㅅ@";
    String RANDOM_INTEGER = "@ㅈ@";
    String RANDOM_LONG = "@ㅉ@";
}
