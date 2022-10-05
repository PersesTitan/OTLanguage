package bin.orign.variable.set.create;

import bin.apply.Repository;
import bin.apply.sys.item.HpMap;
import bin.exception.VariableException;
import bin.orign.variable.set.get.GetSet;
import bin.token.VariableToken;
import work.StartWork;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateCharacterSet implements
        StartWork, VariableToken, GetSet {
    private final String patternText;
    private final Matcher matcher;
    private final String type;

    public CreateCharacterSet(String type, Map<String, Map<String, Object>> repository) {
        repository.put(type, new HpMap<>());
        this.patternText = startMerge(type, BLANKS, VARIABLE_NAME);
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
        matcher.reset();
        if (matcher.find()) {
            // group : VARIABLE_NAME
            String group = matcher.group().replaceFirst("^\\s*" + type + "\\s*", "");
            variableDefineError(group, repositoryArray[0]);
            // value : 값
            String value = line.replaceFirst(patternText, "").strip();
            LinkedHashSet<Character> set;
            if (value.isBlank()) set = new LinkedHashSet<>();
            else {
                if (value.startsWith(SET_ADD)) set = getCharacterSet(value.substring(SET_ADD.length()));
                else if (value.startsWith(VARIABLE_PUT)) set = getCharacterSet(value.substring(VARIABLE_PUT.length()));
                else throw VariableException.noGrammar();
            }
            repositoryArray[0].get(type).put(group, set);
        }
    }
}
