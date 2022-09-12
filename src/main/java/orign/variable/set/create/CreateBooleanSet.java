package orign.variable.set.create;

import apply.Repository;
import exception.VariableException;
import orign.variable.set.get.GetSet;
import token.VariableToken;
import work.StartWork;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static apply.Repository.noUse;

public class CreateBooleanSet implements
        StartWork, VariableToken, GetSet {
    private final String patternText;
    private final Pattern pattern;
    private final String type;

    public CreateBooleanSet(String type, Map<String, Map<String, Object>> repository) {
        repository.put(type, new HashMap<>());
        this.patternText = startMerge(type, BLANKS, VARIABLE_NAME);
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
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            // group : VARIABLE_NAME
            String group = matcher.group().replaceFirst("^\\s*" + type + "\\s*", "");
            if (noUse.contains(group)) throw VariableException.reservedWorks();
            else if (Repository.getSet(repositoryArray[0]).contains(group)) throw VariableException.sameVariable();
            // value : 값
            String value = line.replaceFirst(patternText, "").strip();
            Set<String> set;
            if (value.isBlank()) set = new LinkedHashSet<>();
            else {
                if (value.startsWith(SET_ADD)) set = getBoolSet(value.substring(SET_ADD.length()));
                else throw VariableException.noGrammar();
            }
            repositoryArray[0].get(type).put(group, set);
        }
    }
}
