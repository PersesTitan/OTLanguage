package bin.orign.variable;

import bin.exception.VariableException;
import bin.token.LoopToken;

import java.util.Map;

import static bin.apply.Controller.getSetVariable;

public interface SetVariableValue extends LoopToken {
    default void set(String variableType, String variableName, String value,
                       Map<String, Map<String, Object>> repository) {
        if (!getSetVariable.getType(variableType, value)) throw new VariableException().typeMatch();
        if (ORIGIN_LIST.contains(variableType)) repository.get(variableType).put(variableName, value);
        else if (SET_LIST.contains(variableType)) {
            Object ob = repository.get(variableType).get(variableName);
            getSetVariable.setSet(variableType, ob, value);
        } else if (LIST_LIST.contains(variableType)) {
            Object ob = repository.get(variableType).get(variableName);
            getSetVariable.setList(variableType, ob, value);
        } else if (MAP_LIST.contains(variableType)) {
            Object ob = repository.get(variableType).get(variableName);
            getSetVariable.setMap(variableType, ob, value);
        }
    }
}
