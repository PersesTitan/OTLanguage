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

public class CreateFloatList implements StartWork, VariableToken, GetList {
    private final String patternText;
    private final Matcher matcher;
    private final String type;

    public CreateFloatList(String type, Map<String, Map<String, Object>> repository) {
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
            LinkedList<Float> list;
            if (value.isBlank()) list = new LinkedList<>();
            else {
                if (value.startsWith(LIST_ADD)) list = getFlotList(value.substring(LIST_ADD.length()));
                else if (value.startsWith(VARIABLE_PUT)) list = getFlotList(value.substring(VARIABLE_PUT.length()));
                else throw VariableException.noGrammar();
            }
            repositoryArray[0].get(type).put(group, list);
        }
    }
}
