package bin.string;

import bin.token.MergeToken;
import bin.token.StringToken;
import bin.token.VariableToken;
import work.ReturnWork;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bin.token.LoopToken.ARGUMENT;
import static bin.token.cal.BoolToken.FALSE;
import static bin.token.cal.BoolToken.TRUE;

public class Equals implements ReturnWork, StringToken, VariableToken {
    private final int type;
    private final Matcher matcher;

    public Equals(String className, String type) {
        String start = merge(VARIABLE_GET_S, className, ACCESS, type);
        this.type = start.replace("\\", "").length();
        String patternText = merge(start, ARGUMENT.repeat(2), VARIABLE_GET_E);
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
            String group = matcher.group();
            // :ㅇㅁㅇ~=?[1][63]_ => 1, 63
            String[] tokens = matchSplitError(bothEndCut(group, this.type + 1, 2), BR + BL, 2);
            line = line.replace(group, Objects.equals(tokens[0], tokens[1]) ? TRUE : FALSE);
        }
        return line;
    }

    @Override
    public ReturnWork first() {
        return this;
    }
}
