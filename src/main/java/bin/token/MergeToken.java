package bin.token;

import bin.apply.Repository;
import bin.apply.sys.make.StartLine;
import bin.exception.FileException;
import bin.exception.MatchException;
import bin.exception.VariableException;

import java.io.File;
import java.util.*;

import static bin.apply.Repository.containsVariable;
import static bin.apply.sys.item.Separator.SEPARATOR_FILE;
import static bin.apply.sys.make.StartLine.startStartLine;
import static bin.token.LoopToken.*;
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
        return count >= repLen ? -1 : count;
    }

    static int access(String line, int repLen) {
        int count = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ACCESS.charAt(0)) count++;
            else break;
        }
        return count >= repLen ? -1 : count;
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

    default String getLoopTotal(String... input) {
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

    // (test,1,14) => ㅇㅁㅇ 변수명
    default String[] getLoop(String line) {
        line = line.strip();
        // (test,1,14) <= ㅇㅁㅇ 변수명
        if (line.contains(PUTIN_TOKEN)) {
            // (test,1,14), ㅇㅁㅇ 변수명
            String[] tokens = line.split(PUTIN_TOKEN, 2);
            tokens[0] = tokens[0].strip();      // (test,1,14)
            if (!(tokens[0].startsWith("(") && tokens[0].endsWith(")"))) throw new MatchException().grammarError();
            String loop = bothEndCut(tokens[0]);            // test,1,14
            String variable = tokens[1].strip();            // ㅇㅁㅇ 변수명

            StringTokenizer loopToken = new StringTokenizer(loop, ",");
            StringTokenizer variableToken = new StringTokenizer(variable);

            String fileName = loopToken.nextToken();             // test
            String start = loopToken.nextToken();                // 1
            String end = loopToken.nextToken();                  // 14

            String variableType = variableToken.nextToken();     // ㅇㅁㅇ
            String variableName = variableToken.nextToken();     // 변수명

            return new String[]{fileName, start, end, variableType, variableName};
        } else if (line.contains(RETURN_TOKEN)) {
            // (test,1,14) => 변수명
            String[] tokens = line.split(RETURN_TOKEN, 2);
            tokens[0] = tokens[0].strip();      // (test,1,14)
            if (!(tokens[0].startsWith("(") && tokens[0].endsWith(")"))) throw new MatchException().grammarError();
            String loop = bothEndCut(tokens[0]);                // test,1,14
            String variableName = tokens[1].strip();            // ㅇㅁㅇ 변수명

            StringTokenizer loopToken = new StringTokenizer(loop, ",");

            String fileName = loopToken.nextToken();             // test
            String start = loopToken.nextToken();                // 1
            String end = loopToken.nextToken();                  // 14

            return new String[]{fileName, start, end, variableName};
        } else {
            if (!(line.startsWith("(") && line.endsWith(")"))) throw new MatchException().grammarError();
            String loop = bothEndCut(line.strip());     // (test,1,14) -> test,1,14

            StringTokenizer loopToken = new StringTokenizer(loop, ",");

            String fileName = loopToken.nextToken();             // test
            String start = loopToken.nextToken();                // 1
            String end = loopToken.nextToken();                  // 14

            return new String[]{fileName, start, end};
        }
    }

    // fileName : test  // start : 1    // end : 15
    default void start(String fileName, String start, String end,
                       LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String total = getLoopTotal(fileName, start, end);
        String finalTotal = StartLine.getFinalTotal(false, total, fileName);
        startStartLine(finalTotal, total, repositoryArray);
    }

    default boolean loopStart(String fileName, String start, String end,
                              LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String total = getLoopTotal(fileName, start, end);
        String finalTotal = StartLine.getFinalTotal(false, total, fileName);
        return Objects.equals(StartLine.startLoop(finalTotal, fileName, repositoryArray), LoopToken.BREAK);
    }

    // 매개변수 (test,1,10) <= ㅇㅁㅇ 변수
    default String[] cutLoop(String line) {
        int start = line.lastIndexOf('(');
        // args = [1]
        // loop = (test,9,11)  <= ㅇㅁㅇ 라인
        String args = line.substring(0, start).strip().replace(ACCESS, SEPARATOR_FILE);
        String loop = line.substring(start).strip();
        return new String[] {args, loop};
    }

    default void checkFile(File file) {
        if (!file.exists()) throw new FileException().pathNoHaveError();
        else if (!file.isFile()) throw new FileException().isNotFileError();
        else if (!file.canRead()) throw new FileException().noReadError();
    }

    // 변수 값 반환
    default Map<String, Object> getVariable(String variableName,
                                            Map<String, Map<String, Object>> repositoryArray) {
        for (Map.Entry<String, Map<String, Object>> repository : repositoryArray.entrySet()) {
            if (repository.getValue().containsKey(variableName)) return repository.getValue();
        }
        throw new VariableException().variableNameMatch();
    }
}
