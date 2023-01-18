package bin.orign.math.cal;

import bin.exception.VariableException;
import bin.token.MergeToken;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.token.VariableToken.*;
import static bin.token.VariableToken.DOUBLE_VARIABLE;

public class DownPoint extends StartWorkV3 implements MergeToken {
    private final String variableType;
    // 1: variableName
    public DownPoint(String variableType) {
        super(1);
        this.variableType = variableType;
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String variableName = params[0];
        int count = accessCount(variableName, repositoryArray.size());
        if (count == -1) throw new VariableException().localNoVariable();
        variableName = variableName.substring(count);

        Map<String, Object> variable = getVariable(variableName, repositoryArray.get(count));
        Object variableValue = variable.get(variableName);
        variable.put(variableName, getDownValue(this.variableType, variableValue));
    }

    private Object getDownValue(String variableType, Object value) {
        return switch (variableType) {
            case INT_VARIABLE -> {
                if (value instanceof Integer i) yield --i;
                else throw new VariableException().typeMatch();
            }
            case LONG_VARIABLE -> {
                if (value instanceof Long i) yield --i;
                else throw new VariableException().typeMatch();
            }
            case FLOAT_VARIABLE -> {
                if (value instanceof Float i) yield --i;
                else throw new VariableException().typeMatch();
            }
            case DOUBLE_VARIABLE -> {
                if (value instanceof Double i) yield --i;
                else throw new VariableException().typeMatch();
            }
            default -> throw new VariableException().typeMatch();
        };
    }
}