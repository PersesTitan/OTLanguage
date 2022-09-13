package token.cal;

import static token.Token.DOT;

public interface NumberToken {
    // NUMBER
    String INT = "\\-?[0-9]+";             // -1234, 1234
    String FLOAT = INT + DOT + "[0-9]+(E[0-9]+)?";   // -1234.34, 1234.34
    String NUMBER = "(" + FLOAT + "|" + INT + ")";

    // OPERATOR
    String PLUS_CALCULATOR = "ㅇ\\+ㅇ";
    String MINUS_CALCULATOR = "ㅇ\\-ㅇ";
    String DIVIDE_CALCULATOR = "ㅇ\\/ㅇ";
    String MULTIPLY_CALCULATOR = "ㅇ\\*ㅇ";
    String REMAINDER_CALCULATOR = "ㅇ\\%ㅇ";

    // RANDOM
    String RANDOM_BOOL = "@ㅂ@";
    String RANDOM_DOUBLE = "@ㅆ@";
    String RANDOM_FLOAT = "@ㅅ@";
    String RANDOM_INTEGER = "@ㅈ@";
    String RANDOM_LONG = "@ㅉ@";
}
