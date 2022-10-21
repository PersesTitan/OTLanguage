package bin.string;

import bin.orign.GetOnlyChangeList;
import bin.token.LoopToken;
import bin.token.StringToken;
import work.ReturnWork;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bin.token.cal.BoolToken.FALSE;
import static bin.token.cal.BoolToken.TRUE;

public class Contains implements LoopToken, StringToken, ReturnWork {
    private final int length;
    private final Matcher matcher;

    // ㅇㅁㅇ~ㅉㅇㅉ[반복할 값][리스트]
    public Contains(String type) {
        String pattern = merge(VARIABLE_GET_S, STRING_VARIABLE, ACCESS, type);
        this.length = pattern.replace("\\", "").length();
        String patternText = merge(pattern, ARGUMENT.repeat(2), VARIABLE_GET_E);
        this.matcher = Pattern.compile(patternText).matcher("");
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
            // :ㅇㅁㅇ~ㅉㅇㅉ[반복할 값][리스트]_
            String group = matcher.group();
            // :ㅇㅁㅇ~ㅉㅇㅉ[반복할 값][리스트]_ => 반복할 값, 리스트
            String[] tokens =
                    matchSplitError(
                            bothEndCut(group, length+1, 2),
                            BR + BL, 2);
            line = line.replace(group, tokens[0].contains(tokens[1]) ? TRUE : FALSE);
        }
        return line;
    }

    @Override
    public ReturnWork first() {
        return this;
    }
}