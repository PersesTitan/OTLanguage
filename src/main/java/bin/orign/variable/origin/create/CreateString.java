package bin.orign.variable.origin.create;

import bin.apply.Controller;
import bin.apply.Repository;
import bin.apply.sys.item.HpMap;
import bin.exception.VariableException;
import bin.token.VariableToken;
import work.StartWork;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateString implements StartWork, VariableToken, Controller {
    private final Matcher matcher;
    private final String type;

    public CreateString(String type) {
        String patternText = startMerge(type, BLANKS, VARIABLE_NAME, VARIABLE_PUT);
        this.matcher = Pattern.compile(patternText).matcher("");
        this.type = type;
    }

    @Override
    public boolean check(String line) {
        return matcher.reset(line).find();
    }

    @Override
    public void start(String line, String origen, Map<String, Map<String, Object>>[] repositoryArray) {
        line = line.replaceFirst(startMerge(type, BLANKS), "");
        String variableName = line.split(VARIABLE_PUT)[0];  // 변수명
        String value = line.replaceFirst(VARIABLE_NAME + VARIABLE_PUT, ""); // 값
        variableDefineError(variableName, repositoryArray[0]);
        repositoryArray[0].get(type).put(variableName, value);
    }

    @Override
    public void first() {

    }
}
