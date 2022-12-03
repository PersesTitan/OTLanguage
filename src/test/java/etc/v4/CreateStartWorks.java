package etc.v4;

import work.v4.StartWork;

import java.util.Arrays;
import java.util.Map;
import java.util.StringTokenizer;

import static bin.apply.Repository.*;
import static bin.token.LoopToken.*;
import static bin.token.Token.BL;
import static bin.token.Token.BR;
import static bin.token.VariableToken.VARIABLE_ALL;
import static etc.v4.RepositoryTest.priorityStartWorks;
import static etc.v4.RepositoryTest.startWorks;

public interface CreateStartWorks {
//    enum StartItem {
//        VARIABLE, METHOD, ETC
//    }
//
//    static boolean start(String line, boolean priority) {
//        // 클래스~메소드[값1][값2]
//        // 클래스~메소드 값1
//        String[] tokens = line.split("(?=" + BLANKS + "|" + BL + ")", 2);
//        String local = tokens[0];
//        String value = tokens.length == 2
//                ? tokens[1]
//                : "";
//
//        final String loop;
//        // (test,1,25) or ((test,1,25) => or <=)
//        if (value.contains("(") && (value.strip().endsWith(")")
//                || value.contains(RETURN_TOKEN) || value.contains(PUTIN_TOKEN))) {
//            // ~~~ (test,1,25)
//            //     ^^
//            int loopPoison = value.lastIndexOf('(');
//            loop = value.substring(loopPoison).strip();         // (test,1,10), (test,1,10) => index.html
//            value = value.substring(0, loopPoison).strip();     // [값1][값2]
//        } else loop = null;
//
//        // ex) [값1][값2
//        if (!value.endsWith("]")) return false;
//        String[] params = value.startsWith("[")
//                ? bothEndCut(value).split(BR + BL, count(value))
//                : new String[]{value.stripLeading()};
//        // [값1][값2] => 값1, 값2
//
//        // 클래스~메소드
//        StringTokenizer tokenizer = new StringTokenizer(local, ACCESS);
//        if (!tokenizer.hasMoreTokens()) return false;
//        String className = tokenizer.nextToken();               // 클래스
//        String methodName = tokenizer.hasMoreTokens()           // 메소드, ""
//                ? tokenizer.nextToken("").substring(1)
//                : "";
//        StartWork startWork = getStartWork(className, methodName, priority, bin.CreateStartWorks.Item.ETC);
//        if (startWork != null) {
//            Object[] obs = startWork.paramsCheck(params.length, params);
//            startWork.start(line, obs, loop);
//            return true;
//        } else return !priority && getStartWork(line, params, loop);
//    }
//
//    private static boolean getStartWork(String line, String[] params, String loop) {
//        String[] tokens = line.split("(?!" + VARIABLE_ALL + ")", 2);
//        String local = tokens[0];           // 변수명, 메소드명
//        var startWork = getStartWork(null, local, true, bin.CreateStartWorks.Item.VARIABLE);
//        if (startWork != null) {
//            Object[] obs = startWork.paramsCheck(2, params);
//            startWork.start(line, obs, null);
//            return true;
//        } else if (tokens.length == 2 && (startWork = getStartWork(tokens[0], tokens[1], false, bin.CreateStartWorks.Item.METHOD)) != null) {
//            startWork.start(line, params, loop);
//            return true;
//        } else return false;
//    }
//
//    // StartWork 반환
//    private static StartWork getStartWork(String klassName, String methodName, boolean priority,
//                                            bin.CreateStartWorks.Item startItem) {
//        if (startItem.equals(bin.CreateStartWorks.Item.VARIABLE)) {
//            int count = variable.accessCount(methodName, repository.size());
//            if (count == -1) return null;
//            return containsVariable(methodName.substring(count), repository.get(count))
//                    ? RepositoryTest.startVariable
//                    : null;
//        } else if (startItem.equals(bin.CreateStartWorks.Item.METHOD)) {
//            if (repository.get(0).get(METHOD).containsKey(klassName)
//                    && methodName.startsWith("[")
//                    && methodName.endsWith("]")) return methodVoid;
//        }
//
//        var map = priority
//                ? priorityStartWorks
//                : startWorks;
//        Map<String, StartWork> startWork;
//        if (map.containsKey(klassName)
//                && (startWork = map.get(klassName)).containsKey(methodName))
//            return startWork.get(methodName);
//        return null;
//    }
//
//    // loop 가 있는지 확인하는 로직
//    private static String[] getCheck(String value) {
//        if (value.contains("(") && (value.strip().endsWith(")")
//                || value.contains(RETURN_TOKEN) || value.contains(PUTIN_TOKEN))) {
//            int loopPoison = value.lastIndexOf('(');
//            String loop = value.substring(loopPoison);          // (test,1,10), (test,1,10) => index.html
//            value = value.substring(0, loopPoison).strip();     // [값1][값2]
//            int count = count(value);                           // 2
//            if (!value.endsWith("]")) return null;
//            // value 쪼개기
//            String[] values = Arrays.copyOf(bothEndCut(value).split(BR + BL, count), count+1);
//            values[count] = loop;
//            return values;
//        } else return value.endsWith("]")
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
//    private static String bothEndCut(String text) {
//        return text.substring(1, text.length() - 1);
//    }
}
