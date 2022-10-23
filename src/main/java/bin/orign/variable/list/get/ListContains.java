package bin.orign.variable.list.get;

import bin.exception.VariableException;
import bin.orign.variable.both.ContainsTool;
import bin.token.VariableToken;
import work.ReturnWork;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListContains implements ReturnWork, VariableToken, ContainsTool {
    private final Matcher matcher;

    // 변수명??[]
    public ListContains(String type) {
        String patternText = VARIABLE_GET_S + VARIABLE_ACCESS + type + VARIABLE_GET_E;
        this.matcher = Pattern.compile(patternText).matcher("");
    }

    @Override
    public boolean check(String line) {
        return matcher.reset(line).find();
    }

    @Override
    public String start(String line, Map<String, Map<String, Object>>[] repositoryArray) {
        matcher.reset();
        while (matcher.find()) {
            String group = matcher.group(); // :~변수명??[ㅁㅁ]_
            String token = bothEndCut(group, 1, 2);   // ~변수명??[ㅁㅁ
            int count = accessCount(token, repositoryArray.length);
            if (count == -1) continue;
            // 변수명??[ㅁㅁ => 변수명, ㅁㅁ
            String[] tokens = matchSplitError(token.substring(count), LIST_ISEMPTY + BL, 2);
            // 변수명
            String list = getValue(tokens[0], repositoryArray[count], tokens[1]);
            if (list != null) line = line.replace(group, list);
        }
        return line;
    }

    @Override
    public ReturnWork first() {
        return this;
    }

    private String getValue(String token, Map<String, Map<String, Object>> repository, String value) {
        for (String list : LIST_LIST) {
            var rep = repository.get(list);
            if (rep.containsKey(token)) return collectionCheck((List<Object>) rep.get(token), list, value);
        } return null;
    }
}
