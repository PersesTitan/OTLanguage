package etc.v4;

import bin.exception.MatchException;
import work.v4.ReturnWork;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bin.apply.Repository.*;
import static bin.token.LoopToken.METHOD;
import static bin.token.Token.ACCESS;
import static bin.token.Token.BL;
import static bin.token.Token.BLANKS;
import static bin.token.Token.BR;
import static bin.token.VariableToken.*;
import static bin.token.VariableToken.VARIABLE_GET_E;
import static etc.v4.RepositoryTest.returnWorks;

public interface CreateReturnWorks {
//    Matcher matcher1 = Pattern.compile(String.format(
//                    "%s[^%s][\\s\\S]*%s([\\s\\S]*%s)?",
//                    VARIABLE_GET_S, VARIABLE_GET_S, VARIABLE_GET_E, VARIABLE_DEFAULT))
//            .matcher("");
//    Matcher matcher2 = Pattern.compile(String.format(
//            "%s[^%s%s]+%s([^%s%s]*%s)?",
//            VARIABLE_GET_S, VARIABLE_GET_S,
//            VARIABLE_GET_E, VARIABLE_GET_E,
//            VARIABLE_GET_E, VARIABLE_DEFAULT, VARIABLE_DEFAULT
//    )).matcher("");
//
//    enum Item {
//        VARIABLE, METHOD, ETC
//    }
//
//    static String start(String line) {
//        line = startMatcher(matcher2.reset(line), line);
//        if (line.contains(VARIABLE_GET_S) && line.contains(VARIABLE_GET_E))
//            return startMatcher(matcher1.reset(line), line);
//        else return line;
//    }
//
//    private static String startMatcher(Matcher matcher, String line) {
//        while (matcher.find()) {
//            String group = matcher.group();
//            // 값_기본값 or 값
//            String variable = bothEndCut(group);
//            String value = null;
//            if (!group.endsWith(VARIABLE_GET_E)) {
//                // 값, 기본값
//                String[] tokens = matchSplitError(variable);
//                variable = tokens[0];
//                value = tokens[1];
//            }
//            String sub;
//            if ((sub = sub(variable, value)) != null) {
//                matcher.reset(line = line.replaceFirst(Pattern.quote(group), sub));
//            }
//        }
//        return line;
//    }
//
//    // 값 반환
//    static String sub(String line, String def) {
//        String[] tokens = line.split("(?=" + BLANKS + "|" + BL + ")", 2);
//        String local = tokens[0];
//        String value = tokens.length == 2 ? tokens[1] : "";
//
//        String[] params = value.startsWith("[")
//                ? getCheck(value)
//                : new String[]{value.stripLeading()};
//        if (params == null) return varSub(line, def, null);
//
//        if (local.startsWith(ACCESS)) {
//            return varSub(local, def, params);
//        }
//        StringTokenizer tokenizer = new StringTokenizer(local, ACCESS);
//        String className = tokenizer.nextToken();
//        String methodName = tokenizer.hasMoreTokens() ? tokenizer.nextToken("").substring(1) : "";
//        ReturnWork startWork = getStartWork(className, methodName, Item.ETC);
//        if (startWork != null) {
//            String v = startWork.paramsCheck(params.length, params[0]).start(line, params);
//            return v == null ? def : v;
//        } else return varSub(line, def, params);
//    }
//
//    private static String varSub(String line, String def, String[] params) {
//        String[] tokens = line.split("(?!" + VARIABLE_ALL + ")", 2);
//        String local = tokens[0];
//        String value = tokens.length == 2 ? tokens[1].stripLeading() : "";
//        var startWork = getStartWork(null, local, Item.VARIABLE);
//        if (startWork != null) return startWork.start(local, new String[]{value});
//            // 메소드 로직
//        else if (params != null
//                && tokens.length == 2
//                && (startWork = getStartWork(tokens[0], tokens[1], Item.METHOD)) != null) {
//            return startWork.start(line, params);
//        }
//        else return def;
//    }
//
//    private static ReturnWork getStartWork(String klassName, String methodName, Item returnItem) {
//        if (returnItem.equals(Item.VARIABLE)) {
//            int count = variable.accessCount(methodName, repository.size());
//            if (count == -1) return null;
//            return containsVariable(methodName.substring(count), repository.get(count)) ? variable : null;
//        } else if (returnItem.equals(Item.METHOD)) {
//            if (repository.get(0).get(METHOD).containsKey(klassName)
//                    && methodName.startsWith("[")
//                    && methodName.endsWith("]")) return methodReturn;
//        }
//
//        Map<String, ReturnWork> returnWork;
//        if (returnWorks.containsKey(klassName)
//                && (returnWork = returnWorks.get(klassName)).containsKey(methodName))
//            return returnWork.get(methodName);
//        return null;
//    }
//
//    private static String[] getCheck(String value) {
//        return value.endsWith("]")
//                ? bothEndCut(value).split(BR + BL, count(value))
//                : null;
//    }
//
//    private static int count(String value) {
//        int count = 1, i = -1;
//        while ((i = value.indexOf("][", i+1)) != -1) count++;
//        return count;
//    }
//
//    // 기본 로직
//    private static String bothEndCut(String text) {
//        return text.substring(1, text.length() - 1);
//    }
//
//    private static String[] matchSplitError(String value) {
//        String[] values = value.split(VARIABLE_GET_E, 2);
//        if (values.length != 2) throw new MatchException().grammarError();
//        else return values;
//    }
}