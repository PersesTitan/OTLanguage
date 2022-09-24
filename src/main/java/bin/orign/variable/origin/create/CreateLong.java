package bin.orign.variable.origin.create;

import bin.apply.Controller;
import bin.apply.Repository;
import bin.apply.sys.item.HpMap;
import bin.exception.VariableException;
import bin.token.VariableToken;
import work.StartWork;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static bin.check.VariableCheck.isLong;

public class CreateLong implements StartWork, VariableToken, Controller {
    private final Pattern pattern;
    private final String type;

    public CreateLong(String type, Map<String, Map<String, Object>> repository) {
        String patternText = startMerge(type, BLANKS, VARIABLE_NAME, VARIABLE_PUT);
        repository.put(type, new HpMap<>());
        this.pattern = Pattern.compile(patternText);
        this.type = type;
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line, String origen, Map<String, Map<String, Object>>[] repositoryArray) {
        line = line.replaceFirst(startMerge(type, BLANKS), "");
        String variableName = line.split(VARIABLE_PUT)[0];  // 변수명
        String value = line.replaceFirst(VARIABLE_NAME + VARIABLE_PUT, ""); // 값
        variableDefineError(variableName, repositoryArray[0]);
        if (!isLong(value)) throw VariableException.typeMatch();
        repositoryArray[0].get(type).put(variableName, value);
    }
}
