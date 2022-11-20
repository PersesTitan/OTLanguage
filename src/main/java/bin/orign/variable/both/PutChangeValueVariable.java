package bin.orign.variable.both;

import bin.calculator.tool.Calculator;

import java.util.LinkedList;
import java.util.Map;

import static bin.check.VariableCheck.*;
import static bin.token.VariableToken.*;
import static bin.token.cal.BoolToken.FALSE;
import static bin.token.cal.BoolToken.TRUE;

public interface PutChangeValueVariable extends Calculator {
    default String getOrigin(String variableType, String value,
                             LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        return switch (variableType) {
            case INT_VARIABLE -> {
                if (isInteger(value)) yield value;
                else yield getNumberStr(value, repositoryArray);
            }
            case FLOAT_VARIABLE -> {
                if (isFloat(value)) yield value;
                else yield getNumberStr(value, repositoryArray);
            }
            case LONG_VARIABLE -> {
                if (isLong(value)) yield value;
                else yield getNumberStr(value, repositoryArray);
            }
            case DOUBLE_VARIABLE -> {
                if (isDouble(value)) yield value;
                else yield getNumberStr(value, repositoryArray);
            }
            case BOOL_VARIABLE -> {
                if (isBoolean(value)) yield value;
                else yield Calculator.getBool(value, repositoryArray) ? TRUE : FALSE;
            }
            default -> value;
        };
    }
}
