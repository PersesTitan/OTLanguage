package bin;

import bin.exception.MatchException;
import work.v3.ReturnWorkV3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bin.apply.Repository.*;
import static bin.token.LoopToken.METHOD;
import static bin.token.VariableToken.*;

public interface CreateReturnWorks {
    Matcher matcher1 = Pattern.compile(String.format(
            "%s[^%s][\\s\\S]*%s([\\s\\S]*%s)?",
            VARIABLE_GET_S, VARIABLE_GET_S, VARIABLE_GET_E, VARIABLE_DEFAULT))
            .matcher("");
    Matcher matcher2 = Pattern.compile(String.format(
            "%s[^%s%s]+%s([^%s%s]*%s)?",
            VARIABLE_GET_S, VARIABLE_GET_S,
            VARIABLE_GET_E, VARIABLE_GET_E,
            VARIABLE_GET_E, VARIABLE_DEFAULT, VARIABLE_DEFAULT
    )).matcher("");

    enum ReturnItem {
        VARIABLE, METHOD, ETC
    }

    static String start(String line, LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        line = startMatcher(matcher2.reset(line), line, repositoryArray);
        if (line.contains(VARIABLE_GET_S) && line.contains(VARIABLE_GET_E))
            return startMatcher(matcher1.reset(line), line, repositoryArray);
        else return line;
    }

    private static String startMatcher(Matcher matcher, String line,
                                       LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
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
                matcher.reset(line = line.replaceFirst(Pattern.quote(group), sub));
            }
        }
        return line;
    }

    // 값 반환
    static String sub(String line, String def,
                              LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String[] tokens = line.split("(?=" + BLANKS + "|" + BL + ")", 2);
        String local = tokens[0];
        String value = tokens.length == 2 ? tokens[1] : "";

        String[] params = value.startsWith("[")
                ? getCheck(value)
                : new String[]{value.stripLeading()};
        if (params == null) return varSub(line, def, null, repositoryArray);

        if (local.startsWith(ACCESS)) {
            return varSub(local, def, params, repositoryArray);
        }
        StringTokenizer tokenizer = new StringTokenizer(local, ACCESS);
        String className = tokenizer.nextToken();
        String methodName = tokenizer.hasMoreTokens() ? tokenizer.nextToken("").substring(1) : "";
        ReturnWorkV3 startWork = getStartWork(className, methodName, repositoryArray, ReturnItem.ETC);
        if (startWork != null) {
            String v = startWork.paramsCheck(params.length, params[0]).start(line, params, repositoryArray);
            return v == null ? def : v;
        } else return varSub(line, def, params, repositoryArray);
    }

    private static String varSub(String line, String def, String[] params,
                                 LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String[] tokens = line.split("(?!" + VARIABLE_ALL + ")", 2);
        String local = tokens[0];
        String value = tokens.length == 2 ? tokens[1].stripLeading() : "";
        var startWork = getStartWork(null, local, repositoryArray, ReturnItem.VARIABLE);
        if (startWork != null) return startWork.start(local, new String[]{value}, repositoryArray);
        // 메소드 로직
        else if (params != null
                && tokens.length == 2
                && (startWork = getStartWork(tokens[0], tokens[1], repositoryArray, ReturnItem.METHOD)) != null) {
            return startWork.start(line, params, repositoryArray);
        }
        else return def;
    }

    private static ReturnWorkV3 getStartWork(String klassName, String methodName,
                                             LinkedList<Map<String, Map<String, Object>>> repositoryArray,
                                             ReturnItem returnItem) {
        if (returnItem.equals(ReturnItem.VARIABLE)) {
            int count = variable.accessCount(methodName, repositoryArray.size());
            if (count == -1) return null;
            return containsVariable(methodName.substring(count), repositoryArray.get(count)) ? variable : null;
        } else if (returnItem.equals(ReturnItem.METHOD)) {
            if (repositoryArray.get(0).get(METHOD).containsKey(klassName)
                    && methodName.startsWith("[")
                    && methodName.endsWith("]")) return methodReturn;
        }

        Map<String, ReturnWorkV3> returnWork;
        if (returnWorksV3.containsKey(klassName)
                && (returnWork = returnWorksV3.get(klassName)).containsKey(methodName))
            return returnWork.get(methodName);
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
        if (values.length != 2) throw new MatchException().grammarError();
        else return values;
    }
}
