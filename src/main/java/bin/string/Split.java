package bin.string;

import bin.token.LoopToken;
import bin.token.StringToken;
import work.ReturnWork;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Split implements LoopToken, StringToken, ReturnWork {
    private final int length;
    private final Matcher matcher;

    // ㅇㅁㅇ~ㅅㅍㅅ[기본값][자르고 싶은 조건]
    public Split(String type) {
        String pattern = merge(VARIABLE_GET_S, STRING_VARIABLE, ACCESS, type);
        this.length = pattern.length();
        String patternText = merge(pattern, ARGUMENT.repeat(2), VARIABLE_GET_E);
        this.matcher = Pattern.compile(patternText).matcher("");
    }

    @Override
    public boolean check(String line) {
        return matcher.reset(line).find();
    }

    @Override
    public String start(String line, LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        matcher.reset();
        while (matcher.find()) {
            // :ㅇㅁㅇ~ㅅㅍㅅ[기본값][자르고 싶은 조건]_
            String group = matcher.group();
            // :ㅇㅁㅇ~ㅅㅍㅅ[기본값][자르고 싶은 조건]_ => 기본값, 자르고 싶은 조건
            String[] tokens =
                    matchSplitError(
                            bothEndCut(group, length+1, 2),
                            BR + BL, 2);
            line = line.replace(group,
                    Arrays.toString(tokens[0].split(Pattern.quote(tokens[1]))));
        }
        return line;
    }

    @Override
    public ReturnWork first() {
        return this;
    }
}
