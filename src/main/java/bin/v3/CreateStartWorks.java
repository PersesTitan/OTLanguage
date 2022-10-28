package bin.v3;

import bin.apply.Repository;
import bin.apply.Setting;
import work.v3.StartWorkV3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;

import static bin.apply.Repository.*;
import static bin.token.LoopToken.PUTIN_TOKEN;
import static bin.token.LoopToken.RETURN_TOKEN;
import static bin.token.Token.*;
import static bin.token.VariableToken.VARIABLE_ALL;
import static bin.token.VariableToken.VAR_TOKEN;

public interface CreateStartWorks {
    static boolean start(String line, boolean priority,
                         LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String[] tokens = line.split("(?=" + BLANKS + "|" + BL + ")", 2);
        String local = tokens[0];
        String value = tokens.length == 2 ? tokens[1] : "";

        String[] params = value.startsWith("[")
                ? getCheck(value)
                : new String[]{value.stripLeading()};
        if (params == null) return false;

        StringTokenizer tokenizer = new StringTokenizer(local, ACCESS);
        String className = tokenizer.nextToken();
        String methodName = tokenizer.hasMoreTokens() ? tokenizer.nextToken("").substring(1) : "";
        StartWorkV3 startWork = getStartWork(className, methodName, priority, repositoryArray);
        if (startWork != null) {
            startWork.paramsCheck(params.length, params[0]).start(line, params, repositoryArray);
            return true;
        } else return getStartWork(line, repositoryArray);
    }

    private static boolean getStartWork(String line, LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String[] tokens = line.split("(?!" + VARIABLE_ALL + ")", 2);
        String local = tokens[0];
        String value = tokens.length == 2 ? tokens[1].stripLeading() : "";
        var startWork = getStartWork(VAR_TOKEN, local, false, repositoryArray);
        if (startWork != null) {
            startWork.start(line, new String[]{local, value}, repositoryArray);
            return true;
        } else return false;
    }

    // StartWork 반환
    private static StartWorkV3 getStartWork(String klassName, String methodName, boolean priority,
                                            LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (klassName.equals(VAR_TOKEN)) {
            int count = variable.accessCount(methodName, repositoryArray.size());
            if (count == -1) return null;
            return containsVariable(methodName.substring(count), repositoryArray.get(count)) ? startVariable : null;
        }
        var map = priority ? priorityStartWorksV3 : startWorksV3;
        Map<String, StartWorkV3> startWork;
        if (map.containsKey(klassName)
                && (startWork = map.get(klassName)).containsKey(methodName))
            return startWork.get(methodName);
        return null;
    }

    private static String[] getCheck(String value) {
        if (value.contains("(")
                && (value.strip().endsWith(")")
                || value.contains(RETURN_TOKEN)
                || value.contains(PUTIN_TOKEN))) {
            int loopPoison = value.lastIndexOf('(');
            String loop = value.substring(loopPoison);          // (test,1,10), (test,1,10) => index.html
            value = value.substring(0, loopPoison).strip();     // [][]
            int count = count(value);                           // 2
            if (!value.endsWith("]")) return null;
            // value 쪼개기
            String[] values = Arrays.copyOf(bothEndCut(value).split(BR + BL, count), count+1);
            values[count] = loop;
            return values;
        } else return value.endsWith("]")
                ? bothEndCut(value).split(BR + BL, count(value))
                : null;
    }

    private static int count(String value) {
        int count = 1, i = -1;
        while ((i = value.indexOf("][", i+1)) != -1) count++;
        return count;
    }

    private static String bothEndCut(String text) {
        return text.substring(1, text.length() - 1);
    }
}
