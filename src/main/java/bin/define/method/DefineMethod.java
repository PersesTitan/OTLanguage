package bin.define.method;

import bin.token.LoopToken;
import work.StartWork;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefineMethod implements LoopToken, StartWork {
    private final Matcher matcher;

    public DefineMethod(String type) {
        // ㅁㅅㅁ 메소드명[ㅇㅅㅇ 매개변수] (test,1,10) => 변수명
        // ㅁㅅㅁ 메소드명[ㅇㅅㅇ 매개변수] (test,1,10)
        String params = orMerge(TOTAL_LIST) + BLANKS + VARIABLE_HTML;
        String patternText = startEndMerge(
                METHOD, BLANKS, VARIABLE_HTML,
                "(", "(", BL, params, BR, ")+", "|", BL, BR, ")",
                BLANKS, BRACE_STYLE(),
                "(", BLANK, RETURN, ")?");
        this.matcher = Pattern.compile(patternText).matcher("");
    }

    @Override
    public boolean check(String line) {
        return matcher.reset(line).find();
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {
        // ㅁㅅㅁ 메소드명[ㅇㅅㅇ 매개변수] (test,1,10), 변수명
        String[] tokens = line.strip().split(BLANK + RETURN_TOKEN + BLANK, 2);
        // 메소드명, ㅇㅅㅇ 매개변수] (test,1,10)
        String[] methodToken = matchSplitError(matchSplitError(tokens[0], BLANKS, 2)[1], BL, 2);
        // ㅇㅅㅇ 매개변수, (test,1,10)
        // ㅇㅈㅇ 매개변수][ㅇㅅㅇ 매개변수   (test,1,10)
        String[] methodParams = matchSplitError(methodToken[1], BR + BLANKS, 2);

        String[][] params = methodParams[0].isBlank()
            ? new String[0][0]
            : getParams(methodParams[0].split(BR + BL));
        String total = getLoopTotal(methodParams[1])[1];
        String methodName = methodToken[0]; // 메소드명
        if (tokens.length == 1) {   // void

        } else {                    // return

        }
    }

    private String[][] getParams(String[] params) {
        int count = params.length;
        String[][] param = new String[count][2];
        for (int i = 0; i<count; i++) param[i] = matchSplitError(params[i], BLANKS, 2);
        return param;
    }
}
