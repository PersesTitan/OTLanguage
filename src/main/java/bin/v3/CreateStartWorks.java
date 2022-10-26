package bin.v3;

import bin.apply.Repository;
import bin.apply.Setting;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;

import static bin.apply.Repository.startWorksV3;
import static bin.token.Token.*;

public interface CreateStartWorks {
    static void start(String line, String errorMessage,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String[] tokens = line.split("(?=" + BLANKS + "|" + BL + ")", 2);
        String local = tokens[0];
        String value = tokens.length == 2 ? tokens[1] : "";

        String[] params = value.startsWith("[")
                ? getCheck(value)
                : new String[]{value.stripLeading()};
        if (params == null) return;

        StringTokenizer tokenizer = new StringTokenizer(local, ACCESS);
        String className = tokenizer.nextToken();
        String methodName = tokenizer.hasMoreTokens() ? tokenizer.nextToken("").substring(1) : "";
        StartWorkV3 startWork = getStartWork(className, methodName);
        if (startWork != null) startWork.paramsCheck(params.length, params[0]).start(line, params, repositoryArray);
        else Setting.runMessage(errorMessage);
    }

    private static StartWorkV3 getStartWork(String klassName, String methodName) {
        Map<String, StartWorkV3> startWork;
        if (startWorksV3.containsKey(klassName)
                && (startWork = startWorksV3.get(klassName)).containsKey(methodName))
            return startWork.get(methodName);
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

    private static String bothEndCut(String text) {
        return text.substring(1, text.length() - 1);
    }
}
