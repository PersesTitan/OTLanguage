package etc.v4.variable;

import bin.exception.VariableException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import static bin.token.VariableToken.*;

public interface MethodItemToolTest {
    static void resetRepository(Map<String, Map<String, Object>> repository) {
        repository.values().forEach(Map::clear);
    }

    static void setParams(String[][] paramsSetting, Object[] params,
                          Map<String, Map<String, Object>> repository) {
        // 파라미터 갯수가 없는 파라미터
        if (paramsSetting.length == 0) {
            if (params.length == 1 && params[0].toString().isEmpty()) return;
            else throw new VariableException().methodParamsError();
        }
        // 파라미터 갯수가 다를때
        else if (paramsSetting.length != params.length)
            throw new VariableException().methodParamsError();
        for (int i = 0; i<params.length; i++) {
            String variableType = paramsSetting[i][0];
            String variableName = paramsSetting[i][1];
            String variableValue = params[i].toString();
            if (SET_LIST.contains(variableType)
                    || LIST_LIST.contains(variableType)
                    || MAP_LIST.contains(variableType)) {
                repository.get(variableType).put(variableName, VARIABLE_PUT + variableValue);
            } else {
                repository.get(variableType).put(variableName, variableValue);
            }
        }
    }

    // 파라미터 정체 하는 로직
    static String[][] getParams(String[] ps) {
        Set<String> set = new HashSet<>();      // 변수 중복 체크 임시 저장소
        String[][] values = new String[ps.length][2];
        for (int i = 0; i< ps.length; i++) {
            StringTokenizer tokenizer = new StringTokenizer(ps[i]);
            String variableType = tokenizer.nextToken();
            String variableName = tokenizer.nextToken();
            if (!TOTAL_LIST.contains(variableType)) throw new VariableException().noDefine();
            if (set.contains(variableName)) throw new VariableException().sameVariable();
            set.add(variableName);
            values[i][0] = variableType;
            values[i][1] = variableName;
        }
        return values;
    }
}
