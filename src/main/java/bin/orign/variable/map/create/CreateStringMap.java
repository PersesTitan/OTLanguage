package bin.orign.variable.map.create;

import bin.apply.sys.item.HpMap;
import bin.exception.VariableException;
import bin.orign.variable.map.get.GetMap;
import bin.token.VariableToken;
import work.StartWork;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateStringMap implements StartWork, VariableToken, GetMap {
    private final String patternText;
    private final Matcher matcher;
    private final String type;

    public CreateStringMap(String type) {
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
            Map<String, String> map;
            if (value.isBlank()) map = new LinkedHashMap<>();
            else {
                if (value.startsWith(MAP_ADD)) map = getStringMap(value.substring(MAP_ADD.length()));
                else if (value.startsWith(VARIABLE_PUT)) map = getStringMap(value.substring(VARIABLE_PUT.length()));
                else throw VariableException.noGrammar();
            }
            repositoryArray[0].get(type).put(group, map);
        }
    }

    @Override
    public void first() {

    }
}

