package bin.orign.variable.list.get;

import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.Token;
import bin.token.VariableToken;
import work.StartWork;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListSort implements
        StartWork, Token, VariableToken {
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
                      Map<String, Map<String, Object>>[] repositoryArray) {
        line = line.strip();
        int count = accessCount(line);
        line = line.replaceFirst(START + ACCESS + "+", "");

        String variableName = line.replaceFirst(type + END, "");
        if (count > repositoryArray.length) throw VariableException.localNoVariable();
        var repository = repositoryArray[count];

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

    private void getList(String type, Object ob) {
        switch (type) {
            case LIST_INTEGER -> {
                LinkedList<Integer> list = (LinkedList<Integer>) ob;
                Collections.sort(list);
            }
            case LIST_LONG -> {
                LinkedList<Long> list = (LinkedList<Long>) ob;
                Collections.sort(list);
            }
            case LIST_BOOLEAN -> {
                LinkedList<String> list = (LinkedList<String>) ob;
                Collections.sort(list);
            }
            case LIST_CHARACTER -> {
                LinkedList<Character> list = (LinkedList<Character>) ob;
                Collections.sort(list);
            }
            case LIST_FLOAT -> {
                LinkedList<Float> list = (LinkedList<Float>) ob;
                Collections.sort(list);
            }
            case LIST_DOUBLE -> {
                LinkedList<Double> list = (LinkedList<Double>) ob;
                Collections.sort(list);
            }
            default -> {
                LinkedList<String> list = (LinkedList<String>) ob;
                Collections.sort(list);
            }
        };
    }
}
