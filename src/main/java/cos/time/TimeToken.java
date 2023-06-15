package cos.time;

public interface TimeToken {
    String DATE_TIME = "ㅅㄱㅆ";
    String TIME = "ㅅㄱㅅ";
    String DATE = "ㅆㄱㅆ";

    String YEAR     = "ㄴㄴㄴ";    // 년
    String MONTH    = "ㅇㅂㅇ";    // 월
    String WEEK     = "ㅈㅅㅈ";    // 주
    String DAY      = "ㅇㄹㅇ";    // 일
    String HOUR     = "ㅅㄱㅅ";    // 시
    String MINUTE   = "ㅂㄴㅂ";    // 분
    String SECOND   = "ㅊㅅㅊ";    // 초
    String NANO     = "ㅁㄹㅁ";    // 밀리초

    String IS_BEFORE = "";
    String IS_AFTER = "";

    static void checkValue(int value, int min, int max) {
        if (min > value || max < value)
            throw TimeException.VALUE_VALID.getThrow(min + "<=" + value + "<=" + max);
    }
}
