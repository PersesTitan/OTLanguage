package bin.token;

import static bin.token.Token.QUESTION;
import static bin.token.Token.QUESTION_S;

public interface StringToken {
    String JOIN = "ㅉㅇㅉ";
    String SPLIT = "ㅅㅍㅅ";
    String SPLIT_REGULAR = "ㅆㅍㅆ";
    String CONTAINS = QUESTION_S;
    String EQUALS = "=" + QUESTION_S;

    String CONTAINS_S = QUESTION_S;         // ?
    String EQUALS_S = "=" + QUESTION_S;     // =?
    String PATTERN = "ㅍㅌㅍ";

    String TO_LOWER = "ㅅㅁㅅ";
    String TO_UPPER = "ㄷㅁㄷ";

    String INDEX = "ㅇㅊㅇ";
    String LAST_INDEX = "ㅇㅈㅇ";
    String SUBSTRING = "ㅋㅌㅋ";

    String ADD_ALL = "ㅎㅈㅎ";        // 합집합
    String RETAIN_ALL = "ㄱㅈㅎ";     // 교집합
    String REMOVE_ALL = "ㅊㅈㅎ";     // 차집합
    String CONTAINS_ALL = "ㅂㅈㅎ";   // 부분 집합

    // MATH
    String ROUND = "ㅂㅇㅂ";      // 반올림
    String CEIL = "ㅇㄹㅇ";       // 올림
    String FLOOR = "ㄴㄹㄴ";      // 내림
    String ABS = "ㅈㄷㅈ";        // 절댓값

    String MAX = "ㅋㄴㅋ";     // 큰 값
    String MIN = "ㅈㄱㅈ";     // 작은 값
}
