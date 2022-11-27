package cos.shell.tool;

import bin.apply.Setting;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.MergeToken;
import cos.shell.CreateShell;

import java.util.*;

import static bin.apply.sys.item.Separator.SEPARATOR_LINE;
import static bin.token.LoopToken.SHELL;
import static bin.token.LoopToken.SHELL_START;
import static bin.token.VariableToken.*;
import static bin.token.cal.BoolToken.FALSE;
import static bin.token.cal.BoolToken.TRUE;
import static cos.shell.CreateShell.sh;

public interface ShellTool extends MergeToken {
    default void check(String variableType, Collection<?> collection) {
        switch (variableType) {
            case SET_INTEGER, LIST_INTEGER, MAP_INTEGER -> collection.forEach(v -> {
                if (!(v instanceof Integer)) throw new VariableException().typeMatch();});
            case SET_LONG, LIST_LONG, MAP_LONG -> collection.forEach(v -> {
                if (!(v instanceof Long)) throw new VariableException().typeMatch();});
            case SET_BOOLEAN, LIST_BOOLEAN, MAP_BOOLEAN -> collection.forEach(v -> {
                if (!(v instanceof Boolean)) throw new VariableException().typeMatch();});
            case SET_STRING, LIST_STRING, MAP_STRING -> collection.forEach(v -> {
                if (!(v instanceof String)) throw new VariableException().typeMatch();});
            case SET_CHARACTER, LIST_CHARACTER, MAP_CHARACTER -> collection.forEach(v -> {
                if (!(v instanceof Character)) throw new VariableException().typeMatch();});
            case SET_FLOAT, LIST_FLOAT, MAP_FLOAT -> collection.forEach(v -> {
                if (!(v instanceof Float)) throw new VariableException().typeMatch();});
            case SET_DOUBLE, LIST_DOUBLE, MAP_DOUBLE -> collection.forEach(v -> {
                if (!(v instanceof Double)) throw new VariableException().typeMatch();});
            default -> throw new VariableException().typeMatch();
        }
    }

    default Object getValue(String line, LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        try {
            StringTokenizer tokenizer = new StringTokenizer(checkValidation(line), ",");
            String total = getLoopTotal(tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken());
            StringBuilder builder = new StringBuilder();
            total.lines()
                    .map(v -> v.substring(v.indexOf(' ') + 1))
                    .forEach(v -> builder.append(v).append(SEPARATOR_LINE));
            if (sh == null) {
                Setting.warringMessage("셀이 생성되지 않았습니다.");
                return null;
            } else return sh.evaluate(builder.toString());
        } finally {
            ORIGIN_LIST.forEach(type -> {
                var repository = repositoryArray.getFirst().get(type);
                if (type.equals(BOOL_VARIABLE)) repository.forEach((k, v) ->
                        repository.put(k, (boolean) sh.getVariable(k) ? TRUE : FALSE));
                else repository.keySet().forEach(k -> repository.put(k, sh.getVariable(k)));
            });
            SET_LIST.forEach(type -> checkSub(repositoryArray, type));
            LIST_LIST.forEach(type -> checkSub(repositoryArray, type));
            MAP_LIST.forEach(type -> repositoryArray.getFirst().get(type).keySet().forEach(k ->
                    check(type, ((LinkedHashMap<String, Object>) sh.getVariable(k)).values())));
        }
    }

    private void checkSub(LinkedList<Map<String, Map<String, Object>>> repositoryArray, String type) {
        var repository = repositoryArray.getFirst().get(type);
        switch (type) {
            case MAP_BOOLEAN -> repository.forEach((k, v) ->
                ((LinkedHashMap<String, Boolean>) sh.getVariable(k)).forEach((k1, v1) ->
                    repository.put(k1, v1 ? TRUE : FALSE)));
            case SET_BOOLEAN -> repository.forEach((k, v) -> {
                    LinkedHashSet<String> booleanRep = (LinkedHashSet<String>) repository.get(k);
                    LinkedHashSet<Boolean> boolSet = (LinkedHashSet<Boolean>) sh.getVariable(k);
                    booleanRep.clear();
                    boolSet.forEach(value -> booleanRep.add(value ? TRUE : FALSE));
                });
            case LIST_BOOLEAN -> repository.forEach((k, v) -> {
                    LinkedList<String> booleanRep = (LinkedList<String>) repository.get(k);
                    LinkedList<Boolean> boolList = (LinkedList<Boolean>) sh.getVariable(k);
                    booleanRep.clear();
                    boolList.forEach(value -> booleanRep.add(value ? TRUE : FALSE));
                });
            default -> repository.keySet().forEach(k -> check(type, (Collection<?>) sh.getVariable(k)));
        }
    }

    private String checkValidation(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line);
        if (!tokenizer.hasMoreTokens()) throw new MatchException().grammarError();
        else if (!tokenizer.nextToken().equals(SHELL + ACCESS + SHELL_START)) throw new MatchException().grammarError();
        if (!tokenizer.hasMoreTokens()) throw new MatchException().grammarError();
        String loop = tokenizer.nextToken();
        if (!(loop.startsWith("(") && loop.endsWith(")"))) throw new MatchException().grammarError();
        return bothEndCut(loop);
    }
}
