package event.token;

import java.util.Arrays;
import java.util.regex.Pattern;

public interface Token {
    // STRING
    String VARIABLE_NAME = "[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z$_-]+[0-9ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z$_-]*";
    String VARIABLE = ":" + VARIABLE_NAME + "_";

    String START = "^";
    String END = "$";

    String BLANK = "\\s+";
    String STAR = "\\*";
    String PIPE = "\\|";
    String HYPHEN = "\\-";
    String PLUS = "\\+";
    String CARET = "\\^";
    String DOT = "\\.";
    String QUESTION = "\\?";
    String SL = "\\(";
    String SR = "\\)";
    String ML = "\\{";
    String MR = "\\}";
    String BL = "\\[";
    String BR = "\\]";

    // NUMBER
    String INT = "-?[0-9]+";             // -1234, 1234
    String FLOAT = INT + DOT + "[0-9]+"; // -1234.34, 1234.34
    String NUMBER = "(" + FLOAT + "|" + INT + ")";
    String PLUS_CALCULATOR = "ㅇ\\+ㅇ";
    String MINUS_CALCULATOR = "ㅇ\\-ㅇ";
    String DIVIDE_CALCULATOR = "ㅇ\\/ㅇ";
    String MULTIPLY_CALCULATOR = "ㅇ\\*ㅇ";
    String REMAINDER_CALCULATOR = "ㅇ\\%ㅇ";

    // BOOLEAN
    String TRUE = "ㅇㅇ";
    String FALSE = "ㄴㄴ";
    String OR = "ㄸ";
    String AND = "ㄲ";
    String NOT = "ㅇㄴ";

    String BIG = "ㅇ>ㅇ";
    String SMALL = "ㅇ<ㅇ";
    String SAME = "ㅇ=ㅇ";
    String BIG_SAME = "ㅇ>=ㅇ";
    String SMALL_SAME = "ㅇ<=ㅇ";

    // VARIABLE
    String INT_VARIABLE = "ㅇㅈㅇ";
    String LONG_VARIABLE = "ㅇㅉㅇ";
    String BOOL_VARIABLE = "ㅇㅂㅇ";
    String STRING_VARIABLE = "ㅇㅁㅇ";
    String CHARACTER_VARIABLE = "ㅇㄱㅇ";
    String FLOAT_VARIABLE = "ㅇㅅㅇ";
    String DOUBLE_VARIABLE = "ㅇㅆㅇ";

    String INT_SET_VARIABLE = "ㄴㅈㄴ";
    String LONG_SET_VARIABLE = "ㄴㅉㄴ";
    String BOOL_SET_VARIABLE = "ㄴㅂㄴ";
    String STRING_SET_VARIABLE = "ㄴㅁㄴ";
    String CHARACTER_SET_VARIABLE = "ㄴㄱㄴ";
    String FLOAT_SET_VARIABLE = "ㄴㅅㄴ";
    String DOUBLE_SET_VARIABLE = "ㄴㅆㄴ";

    String INT_LIST_VARIABLE = "ㄹㅈㄹ";
    String LONG_LIST_VARIABLE = "ㄹㅉㄹ";
    String BOOL_LIST_VARIABLE = "ㄹㅂㄹ";
    String STRING_LIST_VARIABLE = "ㄹㅁㄹ";
    String CHARACTER_LIST_VARIABLE = "ㄹㄱㄹ";
    String FLOAT_LIST_VARIABLE = "ㄹㅅㄹ";
    String DOUBLE_LIST_VARIABLE = "ㄹㅆㄹ";

    String INT_MAP_VARIABLE = "ㅈㅈㅈ";
    String LONG_MAP_VARIABLE = "ㅈㅉㅈ";
    String BOOL_MAP_VARIABLE = "ㅈㅂㅈ";
    String STRING_MAP_VARIABLE = "ㅈㅁㅈ";
    String CHARACTER_MAP_VARIABLE = "ㅈㄱㅈ";
    String FLOAT_MAP_VARIABLE = "ㅈㅅㅈ";
    String DOUBLE_MAP_VARIABLE = "ㅈㅆㅈ";


    static String makeBlank(String...texts) {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(texts).forEach(text -> builder.append(text).append("\\s*"));
        builder.delete(builder.length()-3, builder.length());
        return builder.toString();
    }

    static String make(String...texts) {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(texts).forEach(builder::append);
        return builder.toString();
    }

    static String orPattern(String...texts) {
        StringBuilder builder = new StringBuilder("(");
        Arrays.stream(texts).forEach(text -> builder.append(text).append("|"));
        builder.deleteCharAt(builder.length()-1);
        builder.append(")");
        return builder.toString();
    }

    static String orPatterns(String...texts) {
        StringBuilder builder = new StringBuilder("(");
        Arrays.stream(texts).forEach(text -> builder.append(text).append("|"));
        builder.deleteCharAt(builder.length()-1);
        builder.append(")+");
        return builder.toString();
    }

    static String replacePattern(String line) {
        return line
                .replace("(", SL)
                .replace(")", SR)
                .replace(".", DOT)
                .replace("[", BL)
                .replace("]", BR)
                .replace("{", ML)
                .replace("}", MR)
                .replace("^", CARET)
                .replace("*", STAR)
                .replace("|", PIPE)
                .replace("-", HYPHEN)
                .replace("+", PLUS)
                .replace("?", QUESTION);
    }
}
