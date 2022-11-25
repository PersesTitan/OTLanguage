package cos.shell;

import bin.apply.Setting;
import bin.exception.MatchException;
import bin.token.MergeToken;
import cos.shell.tool.JavaTool;
import work.v3.StartWorkV3;

import java.util.*;

import static bin.apply.sys.item.Separator.SEPARATOR_LINE;
import static bin.token.Token.ACCESS;
import static bin.token.VariableToken.*;
import static cos.shell.JavaRepository.JAVA;
import static cos.shell.JavaRepository.JAVA_START;

public class StartShell extends StartWorkV3 implements MergeToken, JavaTool {
    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        try {
            StringTokenizer tokenizer = new StringTokenizer(checkValidation(line), ",");
            String total = getLoopTotal(tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken());
            StringBuilder builder = new StringBuilder();
            total.lines()
                    .map(v -> v.substring(v.indexOf(' ') + 1))
                    .forEach(v -> builder.append(v).append(SEPARATOR_LINE));
            if (CreateShell.sh == null) Setting.warringMessage("셀이 생성되지 않았습니다.");
            else CreateShell.sh.evaluate(builder.toString());
        } finally {
            ORIGIN_LIST.forEach(type -> {
                var repository = repositoryArray.getFirst().get(type);
                repository.keySet().forEach(k -> repository.put(k, CreateShell.sh.getVariable(k)));
            });
            SET_LIST.forEach(type -> {
                var repository = repositoryArray.getFirst().get(type);
                repository.keySet().forEach(k -> check(type, (Collection<?>) CreateShell.sh.getVariable(k)));
            });
            LIST_LIST.forEach(type -> {
                var repository = repositoryArray.getFirst().get(type);
                repository.keySet().forEach(k -> check(type, (Collection<?>) CreateShell.sh.getVariable(k)));
            });
            MAP_LIST.forEach(type -> {
                var repository = repositoryArray.getFirst().get(type);
                repository.keySet().forEach(k ->
                        check(type, ((LinkedHashMap<String, Object>) CreateShell.sh.getVariable(k)).values()));
            });
        }
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
