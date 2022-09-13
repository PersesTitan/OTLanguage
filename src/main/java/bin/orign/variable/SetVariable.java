package bin.orign.variable;

import bin.token.VariableToken;
import work.ReturnWork;

import java.util.Map;
import java.util.regex.Pattern;

public class SetVariable implements ReturnWork, VariableToken {
    private final static Pattern pattern = Pattern.compile(VARIABLE);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public String start(String line,
                        Map<String, Map<String, Object>>[] repositoryArray) {
        for (int i = 0; i<repositoryArray.length; i++) {
            var repository = repositoryArray[i];
            line = getValue(line, i, repository);
        }
        return line;
    }

    private String getValue(String line, int i,
                            Map<String, Map<String, Object>> repository) {
        for (var rep : repository.values()) {
            for (Map.Entry<String, Object> entry : rep.entrySet()) {
                String k = entry.getKey();
                Object v = entry.getValue();
                String key = VARIABLE_GET_S + ACCESS.repeat(i) + k + VARIABLE_GET_E;
                line = line.replace(key, v.toString());
            }
        }
        return line;
    }
}
