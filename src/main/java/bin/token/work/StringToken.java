package bin.token.work;

import bin.apply.repository.method.KlassMap;
import bin.variable.custom.CustomList;

import java.util.regex.Pattern;

import static work.ResetWork.*;

public interface StringToken {
    String REPLACE = "ㄹㅂㄹ";
    String REPLACE_ALL = "ㄹㅍㄹ";
    String SPLIT = "ㅅㅂㅅ";
    String SPLIT_ALL = "ㅅㅍㅅ";

    String INDEX = "ㅇㄴㅇ";
    String LAST_INDEX = "ㅇㄱㅇ";
    String SUBSTRING = "ㅅㅆㅅ";
    String TRIM = "ㅌㄹㅌ";

    String UPPER_CASE = "ㅇㅍㅇ";
    String LOWER_CASE = "ㄷㅍㄷ";

    static void reset(KlassMap work) {
        work.add(s, TRIM, String::strip, s);
        work.<String, Integer, String>add(s, SUBSTRING, i, String::substring, s);
        work.<String, Integer, Integer, String>add(s, SUBSTRING, i, i, String::substring, s);
        work.<String, String>add(s, UPPER_CASE, String::toUpperCase, s);
        work.<String, String>add(s, LOWER_CASE, String::toLowerCase, s);
        work.<String, String, Integer>add(s, INDEX, s, String::indexOf, i);
        work.<String, String, Integer>add(s, LAST_INDEX, s, String::lastIndexOf, i);
        work.<String, String, String, String>add(s, REPLACE, s, s, String::replace, s);
        work.add(s, REPLACE_ALL, s, s, String::replaceAll, s);
        work.<String, String, CustomList<String>>add(s, SPLIT, s, (a, b) -> new CustomList<>(a.split(Pattern.quote(b))), ls);
        work.<String, String, CustomList<String>>add(s, SPLIT_ALL, s, (a, b) -> new CustomList<>(a.split(b)), ls);
        work.<String, String, Integer, CustomList<String>>add(s, SPLIT, s, i, (a, b, i) -> new CustomList<>(a.split(Pattern.quote(b), i)), ls);
        work.<String, String, Integer, CustomList<String>>add(s, SPLIT_ALL, s, i, (a, b, i) -> new CustomList<>(a.split(b, i)), ls);
    }
}
