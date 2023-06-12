package bin.token;

import bin.token.work.SystemToken;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public interface KlassToken {
    String SYSTEM = "ㅆㅅㅆ";

    String STATIC_METHOD = "ㅁㅆㅁ";
    String METHOD = "ㅁㅅㅁ";
    String KLASS = "ㅋㅅㅋ";
    String IMPORT = "ㅇㅍㅇ";

    // OTHER
    String LIST = "ㄹㄷㄹ";
    String SET = "ㄴㄷㄴ";
    String MAP = "ㅈㄷㅈ";

    // VARIABLE
    String INT_VARIABLE = "ㅇㅈㅇ";
    String LONG_VARIABLE = "ㅇㅉㅇ";
    String BOOL_VARIABLE = "ㅇㅂㅇ";
    String STRING_VARIABLE = "ㅇㅁㅇ";
    String CHARACTER_VARIABLE = "ㅇㄱㅇ";
    String FLOAT_VARIABLE = "ㅇㅅㅇ";
    String DOUBLE_VARIABLE = "ㅇㅆㅇ";

    // SET
    String SET_INTEGER = "ㄴㅈㄴ";
    String SET_LONG = "ㄴㅉㄴ";
    String SET_BOOLEAN = "ㄴㅂㄴ";
    String SET_STRING = "ㄴㅁㄴ";
    String SET_CHARACTER = "ㄴㄱㄴ";
    String SET_FLOAT = "ㄴㅅㄴ";
    String SET_DOUBLE = "ㄴㅆㄴ";

    // LIST
    String LIST_INTEGER = "ㄹㅈㄹ";
    String LIST_LONG = "ㄹㅉㄹ";
    String LIST_BOOLEAN = "ㄹㅂㄹ";
    String LIST_STRING = "ㄹㅁㄹ";
    String LIST_CHARACTER = "ㄹㄱㄹ";
    String LIST_FLOAT = "ㄹㅅㄹ";
    String LIST_DOUBLE = "ㄹㅆㄹ";

    // MAP
    String MAP_INTEGER = "ㅈㅈㅈ";
    String MAP_LONG = "ㅈㅉㅈ";
    String MAP_BOOLEAN = "ㅈㅂㅈ";
    String MAP_STRING = "ㅈㅁㅈ";
    String MAP_CHARACTER = "ㅈㄱㅈ";
    String MAP_FLOAT = "ㅈㅅㅈ";
    String MAP_DOUBLE = "ㅈㅆㅈ";

    Set<String> ORIGINS = Set.of(INT_VARIABLE, LONG_VARIABLE, BOOL_VARIABLE, STRING_VARIABLE, CHARACTER_VARIABLE, FLOAT_VARIABLE, DOUBLE_VARIABLE);
    Set<String> LISTS = Set.of(LIST_INTEGER, LIST_LONG, LIST_BOOLEAN, LIST_STRING, LIST_CHARACTER, LIST_FLOAT, LIST_DOUBLE);
    Set<String> RESERVE = Set.of(STATIC_METHOD, METHOD, KLASS, IMPORT, SystemToken.IF);
    AtomicReference<String> DEFAULT_KLASS = new AtomicReference<>(SYSTEM);
}
