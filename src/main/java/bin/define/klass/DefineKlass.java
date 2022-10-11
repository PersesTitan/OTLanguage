package bin.define.klass;

import bin.exception.VariableException;
import bin.token.LoopToken;
import work.StartWork;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefineKlass implements LoopToken, StartWork {
    private final String type;
    private final Matcher matcher;

    public DefineKlass(String type) {
        this.type = type;
        String params = orMerge(TOTAL_LIST) + BLANKS + VARIABLE_HTML;
        String patternText = startEndMerge(
                type, BLANKS, VARIABLE_HTML,
                "((", BL, params, BR, ")+|", BL, BR, ")",
                BLANKS, BRACE_STYLE);
        this.matcher = Pattern.compile(patternText).matcher("");
    }

    @Override
    public boolean check(String line) {
        return this.matcher.reset(line).find();
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {
        // ㅋㅅㅋ 클래스명[] (test,1,2)    // 클래스명[] (test,1,2)
        line = line.strip().substring(type.length()).strip();
        // 클래스명, ] (test,1,2)
        String[] tokens = matchSplitError(line, BL, 2);
        // ㅇㅈㅇ 변수명, (test,1,2)
        String[] klassParams = matchSplitError(tokens[1], BR + BLANKS, 2);

        String[][] params = klassParams[0].isEmpty()
            ? new String[0][0]
            : getParams(klassParams[0].split(BR + BL));
        // test, 1, 10
        String[] fileInformation = matchSplitError(bothEndCut(klassParams[1]), ",", 3);
        String fileName = fileInformation[0];
        String total = LOOP_TOKEN.get(fileName);
        int start = total.indexOf("\n" + fileInformation[1] + " ");
        int end = total.indexOf("\n" + fileInformation[2] + " ");

        var repository = repositoryArray[0].get(this.type);

    }

    private final Set<String> set = new HashSet<>();
    private String[][] getParams(String[] params) {
        set.clear();
        int count = params.length;
        String[][] param = new String[count][2];
        for (int i = 0; i<count; i++) {
            String[] values = matchSplitError(params[i], BLANKS, 2);
            if (set.contains(values[1])) throw VariableException.sameVariable();
            else set.add(values[1]);
            param[i] = values;
        }
        return param;
    }
}
