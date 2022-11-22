package bin.orign.math;

import bin.calculator.tool.Calculator;
import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.check.VariableCheck.*;
import static bin.check.VariableCheck.getDouble;
import static bin.token.VariableToken.*;
import static bin.token.VariableToken.DOUBLE_VARIABLE;

public class Min extends ReturnWorkV3 implements Calculator {
    // 2
    public Min(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String number1 = getNumberStr(params[0], repositoryArray);
        String number2 = getNumberStr(params[1], repositoryArray);
        if (line.startsWith(INT_VARIABLE))
            return Integer.toString(Math.min(getInteger(number1), getInteger(number2)));
        else if (line.startsWith(LONG_VARIABLE))
            return Long.toString(Math.min(getLong(number1), getLong(number2)));
        else if (line.startsWith(FLOAT_VARIABLE))
            return Float.toString(Math.min(getFloat(number1), getFloat(number2)));
        else if (line.startsWith(DOUBLE_VARIABLE))
            return Double.toString(Math.min(getDouble(number1), getDouble(number2)));
        else return null;
    }
}
