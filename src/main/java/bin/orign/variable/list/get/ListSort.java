package bin.orign.variable.list.get;

import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.Token;
import bin.token.VariableToken;
import work.StartWork;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListSort implements StartWork, Token, VariableToken {
    private final String type;
    private final Matcher matcher;

    public ListSort(String type) {
        String patternText = startEndMerge(VARIABLE_ACCESS, type);
        this.matcher = Pattern.compile(patternText).matcher("");
        this.type = type;
    }

    @Override
    public boolean check(String line) {
        return matcher.reset(line).find();
    }

    @Override
    public void start(String line, String origen,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        line = line.strip();
        int count = accessCount(line, repositoryArray.size());
        if (count == -1) throw VariableException.localNoVariable();
        String variableName = bothEndCut(line, count, type.length());
        var repository = repositoryArray.get(count);

        for (Map.Entry<String, Map<String, Object>> entry : repository.entrySet()) {
            Map<String, Object> values = entry.getValue();
            if (values.containsKey(variableName)) {
                if (!LIST_LIST.contains(entry.getKey())) throw MatchException.grammarError();
                getList(entry.getKey(), values.get(variableName));
                return;
            }
        }
        throw VariableException.noDefine();
    }

    @Override
    public void first() {

    }

    private void getList(String type, Object ob) {
        switch (type) {
            case LIST_INTEGER -> Collections.sort((LinkedList<Integer>) ob);
            case LIST_LONG -> Collections.sort((LinkedList<Long>) ob);
            case LIST_BOOLEAN -> Collections.sort((LinkedList<String>) ob);
            case LIST_CHARACTER -> Collections.sort((LinkedList<Character>) ob);
            case LIST_FLOAT -> Collections.sort((LinkedList<Float>) ob);
            case LIST_DOUBLE -> Collections.sort((LinkedList<Double>) ob);
            default -> Collections.sort((LinkedList<String>) ob);
        }
    }
}
