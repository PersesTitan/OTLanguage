package v4.start;

import bin.exception.MatchException;
import v4.bin.apply.sys.item.TypeMap;
import v4.item.Repository;
import v4.work.ReturnWork;

import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bin.token.MergeToken.access;
import static bin.token.VariableToken.*;
import static bin.token.VariableToken.VARIABLE_DEFAULT;

public interface Return {
    Matcher matcher1 = Pattern.compile(String.format(
                "%s[^%s][\\s\\S]*%s([\\s\\S]*%s)?",
                VARIABLE_GET_S, VARIABLE_GET_S, VARIABLE_GET_E, VARIABLE_DEFAULT))
            .matcher("");
    Matcher matcher2 = Pattern.compile(String.format(
                "%s[^%s%s]+%s([^%s%s]*%s)?",
                VARIABLE_GET_S, VARIABLE_GET_S,
                VARIABLE_GET_E, VARIABLE_GET_E,
                VARIABLE_GET_E, VARIABLE_DEFAULT, VARIABLE_DEFAULT))
            .matcher("");

    // 값 변경 토큰 일치시에 값 치환
    static String start(String line, LinkedList<TypeMap> repositoryArray) {
        line = startMatcher(matcher2.reset(line), line, repositoryArray);
        if (line.contains(VARIABLE_GET_S) && line.contains(VARIABLE_GET_E))
            return startMatcher(matcher1.reset(line), line, repositoryArray);
        else return line;
    }

    // 반환 토큰 인식후 반환
    private static String startMatcher(Matcher matcher, String line, LinkedList<TypeMap> repositoryArray) {
        while (matcher.find()) {
            final String group = matcher.group();
            final String logic, def; // 로직 // 기본값
            // :로직_ 일때
            if (group.endsWith(VARIABLE_GET_E)) {
                logic = bothEndCut(group);
                def = null;
            } else {
                // 기본값 :로직_기본값;
                // :로직_기본값; => [로직, 기본값]
                String[] values = matchSplitError(bothEndCut(group));
                logic = values[0];
                def = values[1];
            }

            String sub;
            if ((sub = replace(logic, def, repositoryArray)) != null) {
                matcher.reset(line = line.replaceFirst(Pattern.quote(group), sub));
            }
        }
        return line;
    }

    // 값을 교체하는 로직
    static String replace(String line, String def, LinkedList<TypeMap> repositoryArray) {
        // ㅇㅁㅇ~ㅌㅅㅌ 변수값     => ㅇㅁㅇ~ㅌㅅㅌ, 변수값
        // ㅇㅁㅇ~ㅌㅅㅌ[값1][값2]  => ㅇㅁㅇ~ㅌㅅㅌ, [값1][값2]
        String[] tokens = line.split("(?=" + BLANKS + "|" + BL + ")", 2);
        // klass = ㅇㅁㅇ~ㅌㅅㅌ, params = 변수값
        String klass = tokens[0];
        String params = tokens.length == 2 ? tokens[1] : "";

        final String[] paramValue;
        // 파라미터 설정하는 로직
        if (params.startsWith(BL_)) {
            if (params.endsWith(BR_)) {
                params = bothEndCut(params);
                paramValue = params.split(BR + BL, count(params));
            }
            else return def;
        } else paramValue = new String[] {params.stripLeading()};

        int count = access(line, repositoryArray.size());
        if (count == -1) return def;

        // ㅇㅁㅇ~ㅌㅅㅌ => [ㅇㅁㅇ, ㅌㅅㅌ]
        StringTokenizer tokenizer = new StringTokenizer(klass, ACCESS);
        ReturnWork work = tokenizer.countTokens() == 1
                ? Repository.returnWorks.get(tokenizer.nextToken()).get("")
                : Repository.returnWorks.get(tokenizer.nextToken()).get(tokenizer.nextToken());

        Object[] param = work.paramsCheck(paramValue.length, paramValue, repositoryArray);
        return work.start(line, param, repositoryArray);
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
