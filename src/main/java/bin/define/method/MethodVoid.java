package bin.define.method;

import bin.apply.Setting;
import bin.define.item.MethodItem;
import bin.define.item.MethodType;
import bin.exception.VariableException;
import bin.token.LoopToken;
import work.StartWork;

import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodVoid implements LoopToken, StartWork {
    private final Matcher matcher;

    public MethodVoid() {
        String patternText = startEndMerge(VARIABLE_HTML, "((", BL, "[\\S\\s]+", BR, ")+|", BL, BR, ")");
        this.matcher = Pattern.compile(patternText).matcher("");
    }

    @Override
    public boolean check(String line) {
        return matcher.reset(line).find();
    }

    @Override
    public void start(String line, String origen,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        // 메소드명[]       // 메소드명[ㅁㄴㅇㄹ]
        // 메소드명         // 메소드명, ㅁㄴㅇㄹ
        String[] methodNames = matchSplitError(bothEndCut(line.strip(), 0, 1), BL, 2);
        String methodName = methodNames[0];
        var repository = repositoryArray.get(0).get(METHOD);
        if (repository.containsKey(methodName)) {
            MethodItem methodItem = (MethodItem) repository.get(methodName);
            if (!methodItem.methodType().equals(MethodType.VOID)) throw new VariableException().methodTypeMatch();
            String[] methodParams = methodNames[1].isEmpty() ? new String[0] : methodNames[1].split(BR + BL);
            methodItem.startVoid(methodParams, repositoryArray);
        } else Setting.runMessage(origen);
    }

    @Override
    public void first() {

    }
}
