package bin.orign.variable.list.get;

import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.Token;
import bin.token.VariableToken;
import work.StartWork;

import java.util.*;
import java.util.regex.Pattern;

public class ListSort implements
        StartWork, Token, VariableToken {
    private final String type;
    private final Pattern pattern;

    public ListSort(String type) {
        String patternText = startEndMerge(VARIABLE_NAME, type);
        this.pattern = Pattern.compile(patternText);
        this.type = type;
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
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

    private int accessCount(String line) {
        int count = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ACCESS.charAt(0)) count++;
            else break;
        }
        return count;
    }

    private void getList(String type, Object ob) {
        switch (type) {
            case LIST_INTEGER -> {
                ArrayList<Integer> list = (ArrayList<Integer>) ob;
                Collections.sort(list);
            }
            case LIST_LONG -> {
                ArrayList<Long> list = (ArrayList<Long>) ob;
                Collections.sort(list);
            }
            case LIST_BOOLEAN -> {
                ArrayList<String> list = (ArrayList<String>) ob;
                Collections.sort(list);
            }
            case LIST_CHARACTER -> {
                ArrayList<Character> list = (ArrayList<Character>) ob;
                Collections.sort(list);
            }
            case LIST_FLOAT -> {
                ArrayList<Float> list = (ArrayList<Float>) ob;
                Collections.sort(list);
            }
            case LIST_DOUBLE -> {
                ArrayList<Double> list = (ArrayList<Double>) ob;
                Collections.sort(list);
            }
            default -> {
                ArrayList<String> list = (ArrayList<String>) ob;
                Collections.sort(list);
            }
        };
    }
}
