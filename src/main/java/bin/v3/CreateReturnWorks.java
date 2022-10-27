package bin.v3;

import bin.exception.MatchException;
import work.v3.ReturnWorkV3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bin.apply.Repository.returnWorksV3;
import static bin.token.VariableToken.*;

public interface CreateReturnWorks {
    Matcher matcher = Pattern.compile(String.format(
            "%s[\\s\\S]+%s([\\s\\S]*%s)?",
            VARIABLE_GET_S, VARIABLE_GET_E, VARIABLE_DEFAULT))
            .matcher("");

    static String start(String line, LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        matcher.reset(line);
        while (matcher.find()) {
            String group = matcher.group();
            // 값_기본값 or 값
            String variable = bothEndCut(group);
            String value = null;
            if (!group.endsWith(VARIABLE_GET_E)) {
                // 값, 기본값
                String[] tokens = matchSplitError(variable);
                variable = tokens[0];
                value = tokens[1];
            }

            String sub;
            if ((sub = sub(variable, value, repositoryArray)) != null) {
                line = line.replaceFirst(Pattern.quote(group), sub);
                matcher.reset(line);
            }
        }
        return line;
    }

    private static String sub(String line, String def,
                              LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String[] tokens = line.split("(?=" + BLANKS + "|" + BL + ")", 2);
        String local = tokens[0];
        String value = tokens.length == 2 ? tokens[1] : "";

        String[] params = value.startsWith("[")
                ? getCheck(value)
                : new String[]{value.stripLeading()};
        if (params == null) return varSub(line, def, repositoryArray);

        StringTokenizer tokenizer = new StringTokenizer(local, ACCESS);
        String className = tokenizer.nextToken();
        String methodName = tokenizer.hasMoreTokens() ? tokenizer.nextToken("").substring(1) : "";
        ReturnWorkV3 startWork = getStartWork(className, methodName);
        if (startWork != null) return startWork.paramsCheck(params.length, params[0]).start(line, params, repositoryArray);
        else return varSub(line, def, repositoryArray);
    }

    private static String varSub(String line, String def,
                                 LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String[] tokens = line.split("(?!" + VARIABLE_ALL + ")", 2);
        String local = tokens[0];
        String value = tokens.length == 2 ? tokens[1].stripLeading() : "";
        var startWork = getStartWork(VAR_TOKEN, local);
        if (startWork != null) return startWork.start(local, new String[]{value}, repositoryArray);
        else return def;
    }

    private static ReturnWorkV3 getStartWork(String klassName, String methodName) {
        Map<String, ReturnWorkV3> returnWorkTestMap;
        if (returnWorksV3.containsKey(klassName)
                && (returnWorkTestMap = returnWorksV3.get(klassName)).containsKey(methodName))
            return returnWorkTestMap.get(methodName);
        return null;
    }

    private static String[] getCheck(String value) {
        return value.endsWith("]")
                ? bothEndCut(value).split(BR + BL, count(value))
                : null;
    }

    private static int count(String value) {
        int count = 1, i = -1;
        while ((i = value.indexOf("][", i+1)) != -1) count++;
        return count;
    }

    // 기본 로직
    private static String bothEndCut(String text) {
        return text.substring(1, text.length() - 1);
    }

    private static String[] matchSplitError(String value) {
        String[] values = value.split(VARIABLE_GET_E, 2);
        if (values.length != 2) throw MatchException.grammarError();
        else return values;
    }
}
