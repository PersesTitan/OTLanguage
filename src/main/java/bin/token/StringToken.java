package bin.token;

import static bin.token.Token.QUESTION;
import static bin.token.Token.QUESTION_S;

public interface StringToken {
    String JOIN = "ㅉㅇㅉ";
    String SPLIT = "ㅅㅍㅅ";
    String SPLIT_REGULAR = "ㅆㅍㅆ";
    String CONTAINS = QUESTION;
    String EQUALS = "=" + QUESTION;

    String CONTAINS_S = QUESTION_S;
    String EQUALS_S = "=" + QUESTION_S;

    String ADD_ALL = "";        // 합집합
    String RETAIN_ALL = "";     // 교집합
    String REMOVE_ALL = "";     // 차집합
    String CONTAINS_ALL = "";   // 부분 집합
}
