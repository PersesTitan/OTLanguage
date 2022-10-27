package bin.orign.variable.set.get;

import bin.exception.VariableException;
import bin.orign.variable.both.ContainsTool;
import bin.token.VariableToken;
import work.ReturnWork;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetContains implements ReturnWork, VariableToken, ContainsTool {
    private final Matcher matcher;

    // 변수명?[]
    public SetContains(String type) {
        String patternText = VARIABLE_GET_S + VARIABLE_ACCESS + type + VARIABLE_GET_E;
        this.matcher = Pattern.compile(patternText).matcher("");
    }

    @Override
    public boolean check(String line) {
        return matcher.reset(line).find();
    }

    @Override
    public String start(String line, LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        matcher.reset();
        while (matcher.find()) {
            String group = matcher.group(); // :~변수명?[ㅁㅁ]_
            String token = bothEndCut(group, 1, 2);   // ~변수명?[ㅁㅁ
            int count = accessCount(token, repositoryArray.size());
            if (count == -1) continue;
            // 변수명?[ㅁㅁ => 변수명, ㅁㅁ
            String[] tokens = matchSplitError(token.substring(count), SET_ISEMPTY + BL, 2);
            // 변수명
            String set = getValue(tokens[0], repositoryArray.get(count), tokens[1]);
            if (set != null) line = line.replace(group, set);
        }
        return line;
    }

    @Override
    public ReturnWork first() {
        return this;
    }

    private String getValue(String token, Map<String, Map<String, Object>> repository, String value) {
        for (String set : SET_LIST) {
            var rep = repository.get(set);
            if (rep.containsKey(token)) return collectionCheck((Set<Object>) rep.get(token), set, value);
        } return null;
    }
}
