package bin.define;

import bin.token.LoopToken;
import work.ReturnWork;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodVoid implements ReturnWork, LoopToken {
    private final Matcher matcher;

    public MethodVoid() {
        String patternText = merge(VARIABLE_GET_S, VARIABLE_HTML, "(", ARGUMENT, ")+", VARIABLE_GET_E);
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
            String group = matcher.group();                                           // :메소드명[]_
            String[] methodName = matchSplitError(bothEndCut(group), BL, 2);    // 메소드명, ]


        }
        return null;
    }
}
