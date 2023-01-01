package bin.string.blank;

import bin.exception.VariableException;
import bin.token.MergeToken;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.token.VariableToken.STRING_VARIABLE;

public class StringTrimPoint extends StartWorkV3 implements MergeToken {
    // 1: 변수명 필수
    public StringTrimPoint() {
        super(1);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String variableName = params[0];
        int count = accessCount(variableName, repositoryArray.size());
        if (count == -1) throw new VariableException().localNoVariable();
        variableName = variableName.substring(count);
        Map<String, Object> repository = repositoryArray.get(count).get(STRING_VARIABLE);
        if (repository.containsKey(variableName)) {
            String value = repository.get(variableName).toString().strip();
            repository.put(variableName, value);
        } else throw new VariableException().variableNameMatch();
    }
}
