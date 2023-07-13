package cos.poison;

import bin.token.ColorToken;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface PoisonToken {
    String POISON = "ㅍㅇㅍ";

    char RESPONSE_TYPE    = '>';  // json, text, html, ...
    char DATA_TYPE        = '<';  // json, url
    char CHARSET_TYPE     = '=';  // utf-8, ...

    String CREATE = "ㅅㅇㅅ";
    String START = "ㅅㅌㅅ";
    String STOP = "ㄲㅌㄲ";
    String REDIRECT = "ㄹㄷㄹ";

    String COOKIE = "ㅋㄱㅋ";
    String DELETE_COOKIE = '-' + COOKIE;
    String SET_COOKIE = '<' + COOKIE;
    String GET_COOKIE = '>' + COOKIE;
    String IS_COOKIE = '?' + COOKIE;

    String SET_HOST = "<ㅎㅅㅎ";
    String SET_PORT = "<ㅍㅌㅍ";

    String POST     = "ㅍㅅㅍ";
    String GET      = "ㄱㅅㄱ";
    String PATCH    = "ㅂㅅㅂ";
    String PUT      = "ㅌㅅㅌ";
    String DELETE   = "ㄷㅅㄷ";

    static void printTime() {
        System.out.printf("%s[%s]%s",
                ColorToken.YELLOW,
                new SimpleDateFormat("yyyy-MM-dd H:mm:ss").format(new Date()),
                ColorToken.RESET);
    }
}
