package bin.orign.variable.origin.create;

import bin.apply.Controller;
import bin.apply.Repository;
import bin.exception.VariableException;
import bin.token.VariableToken;
import work.StartWork;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static bin.check.VariableCheck.isInteger;

public class CreateInteger implements StartWork, VariableToken, Controller {
    private final Pattern pattern;
    private final String type;

    public CreateInteger(String type, Map<String, Map<String, Object>> repository) {
        String patternText = startMerge(type, BLANKS, VARIABLE_NAME, VARIABLE_PUT);
        repository.put(type, new HashMap<>());
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
        if (Repository.noUse.contains(variableName)) throw VariableException.reservedWorks();
        else if (Repository.getSet(repositoryArray[0]).contains(variableName)) throw VariableException.sameVariable();
        else if (!isInteger(value)) throw VariableException.typeMatch();
        repositoryArray[0].get(type).put(variableName, value);
    }
}
