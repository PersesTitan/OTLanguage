package bin.string.pattern;

import work.v3.ReturnWorkV3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Pattern;

import static bin.token.cal.BoolToken.FALSE;
import static bin.token.cal.BoolToken.TRUE;

public class PatternMatch extends ReturnWorkV3 {
    // 2 [원본][패턴]
    public PatternMatch(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        return Pattern.compile(params[1]).matcher(params[0]).find() ? TRUE : FALSE;
    }
}
