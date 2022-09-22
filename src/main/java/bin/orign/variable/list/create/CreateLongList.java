package bin.orign.variable.list.create;

import bin.apply.Repository;
import bin.apply.sys.item.HpMap;
import bin.exception.VariableException;
import bin.token.VariableToken;
import bin.orign.variable.list.get.GetList;
import work.StartWork;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateLongList implements StartWork, VariableToken, GetList {
    private final String patternText;
    private final Pattern pattern;
    private final String type;

    public CreateLongList(String type, Map<String, Map<String, Object>> repository) {
        repository.put(type, new HpMap<>());
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
            if (Repository.noUse.contains(group)) throw VariableException.reservedWorks();
            else if (Repository.getSet(repositoryArray[0]).contains(group)) throw VariableException.sameVariable();
            // value : 값
            String value = line.replaceFirst(patternText, "").strip();
            LinkedList<Long> list;
            if (value.isBlank()) list = new LinkedList<>();
            else {
                if (value.startsWith(LIST_ADD)) list = getLongList(value.substring(LIST_ADD.length()));
                else if (value.startsWith(VARIABLE_PUT)) list = getLongList(value.substring(VARIABLE_PUT.length()));
                else throw VariableException.noGrammar();
            }
            repositoryArray[0].get(type).put(group, list);
        }
    }
}
