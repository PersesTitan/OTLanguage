package v4.start;

import bin.exception.MatchException;
import v4.bin.apply.sys.item.TypeMap;
import v4.item.Repository;
import v4.work.StartWork;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;

import static bin.token.Token.*;
import static bin.token.VariableToken.VARIABLE_GET_E;

public interface Start extends Repository {
    static boolean start(final String line, boolean priority, LinkedList<TypeMap> repositoryArray) {
        final String[] tokens = line.split("(?=" + BLANKS + "|" + BL + ")", 2);
        // local : klass
        final String local = tokens[0];
        final String[] value;
        final String loop;

        StringTokenizer tokenizer = new StringTokenizer(local, ACCESS);
        final String klass = tokenizer.nextToken();
        final String method = tokenizer.countTokens() == 0 ? "" : tokenizer.nextToken();
        HashMap<String, Map<String, StartWork>> work = priority ? priorityStartWorks : startWorks;
        StartWork startWork;
        if (work.containsKey(klass) && work.get(klass).containsKey(method)) startWork = work.get(klass).get(method);
        else return false;
        if (startWork.isLoop()) {
            String token = tokens[1];
            if (token.contains("(") && token.contains(")")) {
                int i = token.lastIndexOf('(');
                loop = token.substring(i).strip();
                tokens[1] = tokens[1].substring(0, i);
            } else throw new MatchException().grammarError();
        } else loop = null;

        if (tokens[1].startsWith(BL_)) {
            String token = bothEndCut(tokens[1]);
            int count = count(token);
            value = token.split(BR + BL, count);
        } else value = new String[] {tokens[1].stripLeading()};

        Object[] objects = startWork.paramsCheck(value.length, value, repositoryArray);
        startWork.start(line, loop, objects, repositoryArray);
        return true;
    }

    // 파라미터 갯수 구하는 로직
    private static int count(String value) {
        int count = 1, i = -1;
        while ((i = value.indexOf("][", i+1)) != -1) count++;
        return count;
    }

    // 양쪽 텍스트 절단
    private static String bothEndCut(String text) {
        return text.substring(1, text.length() - 1);
    }

    // 잘라내기
    private static String[] matchSplitError(String value) {
        String[] values = value.split(VARIABLE_GET_E, 2);
        if (values.length != 2) throw new MatchException().grammarError();
        else return values;
    }
}
