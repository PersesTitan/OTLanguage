package bin.string;

import bin.token.LoopToken;
import bin.token.StringToken;
import work.ReturnWork;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bin.token.cal.BoolToken.FALSE;
import static bin.token.cal.BoolToken.TRUE;

public class KlassString implements LoopToken, StringToken, ReturnWork {
    private static final Map<String, BiFunction<String, String, String>> map = new HashMap<>();
    private final Matcher matcher;
    private final int length;

    static {
        map.put(CONTAINS, (value1, value2) -> value1.contains(value2) ? TRUE : FALSE);
    }

    public KlassString(String klassName, String...types) {
        String pattern = merge(VARIABLE_GET_S, klassName, ACCESS);  // :ㅇㅁㅇ~
        String patternText = merge(pattern, orMerge(types), ARGUMENT.repeat(2), VARIABLE_GET_E);
        this.matcher = Pattern.compile(patternText).matcher("");
        this.length = pattern.length();
    }

    @Override
    public boolean check(String line) {
        return matcher.reset(line).find();
    }

    @Override
    public String start(String line,
                        Map<String, Map<String, Object>>[] repositoryArray) {
        matcher.reset();
        while (matcher.find()) {
            String group = matcher.group();  // :ㅇㅁㅇ~
        }
        return line;
    }

    @Override
    public ReturnWork first() {
        return this;
    }
}
