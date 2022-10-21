package bin.define.method;

import bin.define.item.MethodItem;
import bin.define.item.MethodType;
import bin.exception.VariableException;
import bin.token.LoopToken;
import work.ReturnWork;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodReturn implements LoopToken, ReturnWork {
    private final Matcher matcher;

    public MethodReturn() {
        String patternText = merge(
                VARIABLE_GET_S, VARIABLE_HTML,
                "((", BL, "[\\S\\s]+", BR, ")+|", BL, BR, ")",
                VARIABLE_GET_E);
        this.matcher = Pattern.compile(patternText).matcher("");
    }

    @Override
    public boolean check(String line) {
        return this.matcher.reset(line).find();
    }

    @Override
    public String start(String line,
                        Map<String, Map<String, Object>>[] repositoryArray) {
        matcher.reset();
        while (matcher.find()) {
            // :메소드명[]_     :메소드명[ㅇㅈㅇ ㅁㄴㅇㄹ][ㅇㅅㅇ ㅁㄴㅇㄹ]
            String group = matcher.group();
            // 메소드명,    메소드명, ㅇㅈㅇ][ㅇㅈㅇ ㅁㄴㅇㄹ
            String[] methodNames = matchSplitError(bothEndCut(group.strip(), 1, 2), BL, 2);
            String methodName = methodNames[0];
            var repository = repositoryArray[0].get(METHOD);
            if (repository.containsKey(methodName)) {
                MethodItem methodItem = (MethodItem) repository.get(methodName);
                if (!methodItem.methodType().equals(MethodType.RETURN)) throw VariableException.methodTypeMatch();
                String[] methodParams = methodNames[1].isEmpty() ? new String[0] : methodNames[1].split(BR + BL);

                String oldWord = repositoryArray.length == 1
                        ? methodItem.startReturn(methodParams, repositoryArray[0])
                        : methodItem.startReturn(methodParams, repositoryArray[0], repositoryArray[1]);
                if (oldWord != null) line = line.replace(group, oldWord);
            }
        }
        return line;
    }

    @Override
    public ReturnWork first() {
        return this;
    }
}
