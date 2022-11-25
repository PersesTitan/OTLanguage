package cos.java;

import bin.apply.Setting;
import bin.exception.MatchException;
import bin.token.MergeToken;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;

import static bin.token.Token.ACCESS;
import static cos.java.JavaRepository.JAVA;
import static cos.java.JavaRepository.JAVA_START;

public class StartShell extends StartWorkV3 implements MergeToken {
    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        StringTokenizer tokenizer = new StringTokenizer(checkValidation(line), ",");
        String total = getLoopTotal(tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken());
        StringBuilder builder = new StringBuilder();
        total.lines()
                .map(v -> v.substring(v.indexOf(' ') + 1))
                .forEach(builder::append);
        if (CreateShell.sh == null) Setting.warringMessage("셀이 생성되지 않았습니다.");
        else CreateShell.sh.evaluate(builder.toString());
    }

    private String checkValidation(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line);
        if (!tokenizer.hasMoreTokens()) throw new MatchException().grammarError();
        else if (!tokenizer.nextToken().equals(JAVA + ACCESS + JAVA_START)) throw new MatchException().grammarError();
        if (!tokenizer.hasMoreTokens()) throw new MatchException().grammarError();
        String loop = tokenizer.nextToken();
        if (!(loop.startsWith("(") && loop.endsWith(")"))) throw new MatchException().grammarError();
        return bothEndCut(loop);
    }
}
