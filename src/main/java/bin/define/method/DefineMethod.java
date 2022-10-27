package bin.define.method;

import bin.define.item.MethodItem;
import bin.define.item.MethodType;
import bin.exception.VariableException;
import bin.token.LoopToken;
import work.StartWork;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefineMethod implements LoopToken, StartWork {
    private final String type;
    private final Matcher matcher;

    public DefineMethod(String type) {
        this.type = type;
        // ㅁㅅㅁ 메소드명[ㅇㅅㅇ 매개변수] (test,1,10) => 변수명
        // ㅁㅅㅁ 메소드명[ㅇㅅㅇ 매개변수] (test,1,10)
        String params = orMerge(TOTAL_LIST) + BLANKS + VARIABLE_HTML;
        String patternText = startEndMerge(
                type, BLANKS, VARIABLE_HTML,
                "((", BL, params, BR, ")+|", BL, BR, ")",
                BLANKS, BRACE_STYLE,
                "(", BLANK, RETURN, ")?");
        this.matcher = Pattern.compile(patternText).matcher("");
    }

    @Override
    public boolean check(String line) {
        return matcher.reset(line).find();
    }

    @Override
    public void start(String line, String origen,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        // ㅁㅅㅁ 메소드명[ㅇㅅㅇ 매개변수] (test,1,10), 변수명
        String[] tokens = line.strip().split(BLANK + RETURN_TOKEN + BLANK, 2);
        // 메소드명, ㅇㅅㅇ 매개변수] (test,1,10)
        String[] methodToken = matchSplitError(tokens[0].substring(type.length()).strip(), BL, 2);
        // ㅇㅅㅇ 매개변수, (test,1,10)
        // ㅇㅈㅇ 매개변수][ㅇㅅㅇ 매개변수   (test,1,10)
        String[] methodParams = matchSplitError(methodToken[1], BR + BLANKS, 2);

        // [[ㅇㅈㅇ, 매개변수][ㅇㅅㅇ, 매개변수]]
        String[][] params = methodParams[0].isEmpty()
            ? new String[0][0]
            : getParams(methodParams[0].split(BR + BL));
        // test, 1, 10
        String[] fileInformation = matchSplitError(bothEndCut(methodParams[1]), ",", 3);
        String fileName = fileInformation[0];
        String total = LOOP_TOKEN.get(fileName);
        int start = total.indexOf("\n" + fileInformation[1] + " ");
        int end = total.indexOf("\n" + fileInformation[2] + " ");

        // 메소드명 = methodToken[0]
        var repository = repositoryArray.get(0).get(this.type);
        if (repository.containsKey(methodToken[0])) throw VariableException.definedMethodName();

        MethodType methodType = tokens.length == 1 ? MethodType.VOID : MethodType.RETURN;
        String returnVariable = tokens.length == 1 ? null : tokens[1];
        repository.put(methodToken[0], new MethodItem(params, methodType, returnVariable, fileName, start, end));
    }

    @Override
    public void first() {

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
