package bin.token;

import bin.apply.Repository;
import bin.exception.MatchException;
import bin.exception.VariableException;

import java.util.Arrays;
import java.util.Map;
import java.util.StringTokenizer;

import static bin.apply.Repository.containsVariable;
import static bin.token.LoopToken.LOOP_TOKEN;
import static bin.token.Token.*;

public interface MergeToken {
    default String merge(String...texts) {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(texts).forEach(builder::append);
        return builder.toString();
    }

    default String startEndMerge(String...texts) {
        StringBuilder builder = new StringBuilder(START);
        builder.append(BLANK);
        Arrays.stream(texts).forEach(builder::append);
        builder.append(BLANK).append(END);
        return builder.toString();
    }

    default String orMerge(String...texts) {
        return "("+String.join("|", texts)+")";
    }

    default String startMerge(String...texts) {
        return START + BLANK + String.join("", texts);
    }

    default String blackMerge(String...texts) {
        return String.join(BLANK, texts);
    }

    default String blacksMerge(String...texts) {
        return String.join(BLANKS, texts);
    }

    // 양쪽 끝 삭제
    default String bothEndCut(String text, int start, int end) {
        return text.substring(start, text.length()-end);
    }

    default String bothEndCut(String text) {
        return bothEndCut(text, 1, 1);
    }

    // Iterable
    default String orMerge(Iterable<? extends CharSequence> elements) {
        return "(" + String.join("|", elements) + ")";
    }

    // ACCESS 갯수 세는 로직
    default int accessCount(String line, int repLen) {
        int count = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ACCESS.charAt(0)) count++;
            else break;
        }
        return  count >= repLen ? -1 : count;
    }

    // 변수
    default void variableDefineError(String variableName, Map<String, Map<String, Object>> repository) {
        if (containsVariable(
            variableName.startsWith("[")
                ? variableName.substring(variableName.indexOf("]") + 1)
                : variableName, repository))
            throw new VariableException().sameVariable();
    }

    // 갯수 에러 체크
    default String[] matchSplitError(String value, String pattern, int count) {
        String[] values = value.split(pattern, count);
        if (values.length != count) throw new MatchException().grammarError();
        else return values;
    }

    default String splitNoCutBack(String token) {
        return "(?=" + token + ")";
    }

    default String splitNoCutBack(String...token) {
        return "(?=" + String.join("|", token) + ")";
    }

    // input = (test,1,10)
    default String[] getLoopTotal(String input) {
        // test, 1, 10
        StringTokenizer tokenizer = new StringTokenizer(bothEndCut(input), ",");
        String fileName = tokenizer.nextToken();
        String total = LOOP_TOKEN.get(fileName);
        int start = total.indexOf("\n" + tokenizer.nextToken() + " ");
        int end = total.indexOf("\n" + tokenizer.nextToken() + " ");
        // test, total
        return new String[]{fileName, total.substring(start, end)};
    }

    default String getLoopTotal(String[] input) {
        String total = LOOP_TOKEN.get(input[0]);
        int start = total.indexOf("\n" + input[1] + " ");
        int end = total.indexOf("\n" + input[2] + " ");
        return total.substring(start, end);
    }

    // NO group
    default String getNoMatchFront(String token1) {
        return "(?<=" + token1 + ")";
    }

    default String getNoMatchFront(String token1, String token2) {
        return "(?<=" + token1 + ")" + token2;
    }

    default String getNoMatchBack(String token1, String token2) {
        return token1 + "(?=" + token2 + ")";
    }
}
