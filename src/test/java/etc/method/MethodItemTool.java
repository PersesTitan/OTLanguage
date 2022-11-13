package etc.method;

import bin.exception.VariableException;

import java.util.Map;

import static bin.token.VariableToken.*;
import static bin.token.VariableToken.VARIABLE_PUT;

public interface MethodItemTool {
    static void resetRepository(Map<String, Map<String, Object>> repository) {
        repository.values().forEach(Map::clear);
    }

    static void setParams(String[][] paramsSetting, String[] params,
                           Map<String, Map<String, Object>> repository) {
        // 파라미터 갯수가 없는 파라미터
        if (paramsSetting.length == 0 && params.length == 1 && params[0].isEmpty())
            throw new VariableException().methodParamsError();
            // 파라미터 갯수가 다를때
        else if (paramsSetting.length != params.length)
            throw new VariableException().methodParamsError();
        for (int i = 0; i<params.length; i++) {
            String variableType = paramsSetting[i][0];
            String variableName = paramsSetting[i][1];
            String variableValue = params[i];
            if (SET_LIST.contains(variableType)
                    || LIST_LIST.contains(variableType)
                    || MAP_LIST.contains(variableType)) {
                repository.get(variableType).put(variableName, VARIABLE_PUT + variableValue);
            } else {
                repository.get(variableType).put(variableName, variableValue);
            }
        }
    }
}
