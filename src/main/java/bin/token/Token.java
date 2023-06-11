package bin.token;

import bin.apply.calculator.number.NumberToken;

import java.util.Set;
import java.util.regex.Pattern;

public interface Token {
    String VARIABLE = "[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z]+([_-]?[0-9ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z])*";

    String RETURN_TOKEN = "=>";
    String PUT_TOKEN = "<=";
    String COMMA = ",";

    char CLEAR = '!';
    char IS_EMPTY = '?';
    char MAX = '>', MIN = '<';
    String GET = ">>";
    String ADD = "<<";
    String CONTAINS = "?";
    String CONTAINS_V = "??";
    String SUM = "++";

    char FOR = '^';
    char PUT = ':';
    char REMARK = '#';
    char REPLACE_S = ':';
    char REPLACE_E = '_';
    char REPLACE_D = ';';

    char ACCESS = '~';
    char PARAM_S = '[', PARAM_E = ']';
    char LOOP_S = '{', LOOP_E = '}';
    char SET_S = '(', SET_E = ')';
    char LIST_S = '[', LIST_E = ']';
    char MAP_S = '{', MAP_E = '}';

    String PARAM = "][";
    String TRUE = "ㅇㅇ";
    String FALSE = "ㄴㄴ";
    String NOT = "ㅇㄴ";
    char TOKEN = 'ㅇ';
    char OR = 'ㄸ', AND = 'ㄲ';

    Set<Character> BLANKS = Set.of(' ', '\t', '\n', '\r', '\f');
    Set<String> RESERVE = Set.of(
            Token.TRUE, Token.FALSE, Token.NOT,
            Character.toString(TOKEN) + NumberToken.SUB + TOKEN,
            Character.toString(OR),
            Character.toString(AND)
    );
}
