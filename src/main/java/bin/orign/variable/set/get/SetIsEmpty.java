package bin.orign.variable.set.get;

import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.VariableToken;
import work.ReturnWork;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetIsEmpty implements
        GetSet, VariableToken, ReturnWork {
    private final String type;
    private final Pattern pattern;

    public SetIsEmpty(String type) {
        this.type = type;
        String patternText = VARIABLE_GET_S + VARIABLE_ACCESS + type + VARIABLE_GET_E;
        this.pattern = Pattern.compile(patternText);
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public String start(String line, Map<String, Map<String, Object>>[] repositoryArray) {
        StringBuilder builder = new StringBuilder(line);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String group = matcher.group();
            StringBuilder variables = new StringBuilder(group);
            String value = // :~변수?_ => ~변수
                    variables.substring(
                            VARIABLE_GET_S.length(),
                            variables.length()
                                    - VARIABLE_GET_E.length()
                                    - type.replace("\\", "").length());
            variables.setLength(0);
            variables.append(value);
            int count = countAccess(variables);
            if (count > repositoryArray.length) throw VariableException.localNoVariable();

            Set<Object> set = getSet(count, variables.toString(), repositoryArray);
            replace(builder, group, set.isEmpty() ? "ㅇㅇ" : "ㄴㄴ");
        }
        return builder.toString();
    }

    private Set<Object> getSet(int count, String variableName,
                               Map<String, Map<String, Object>>[] repositoryArray) {
        var repository = repositoryArray[count];
        for (Map.Entry<String, Map<String, Object>> entry : repository.entrySet()) {
            Map<String, Object> values = entry.getValue();
            if (values.containsKey(variableName)) {
                if (!SET_LIST.contains(entry.getKey())) throw MatchException.grammarError();
                return (Set<Object>) values.get(variableName);
            }
        }
        throw VariableException.noDefine();
    }

    private void replace(StringBuilder builder, String oldWord, String newWord) {
        int start = builder.indexOf(oldWord);
        int end = start + oldWord.length();
        builder.replace(start, end, newWord);
    }

    private int countAccess(StringBuilder line) {
        int count = 0;
        for (int i = 0; i<line.length(); i++) {
            if (line.charAt(i) == ACCESS.charAt(0)) count++;
            else break;
        }
        line.replace(0, count, "");
        return count;
    }
}
