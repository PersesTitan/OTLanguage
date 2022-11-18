package bin.orign.math.round;

import bin.calculator.tool.Calculator;
import bin.exception.VariableException;
import work.v3.ReturnWorkV3;

import java.lang.invoke.TypeDescriptor;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static bin.check.VariableCheck.*;
import static bin.token.VariableToken.*;

public class Abs extends ReturnWorkV3 implements Calculator {
    // 1
    public Abs(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String value = getNumberStr(params[0], repositoryArray);
        try {
            if (line.startsWith(INT_VARIABLE)) return String.valueOf(Math.abs(getInteger(value)));
            else if (line.startsWith(LONG_VARIABLE)) return String.valueOf(Math.abs(getLong(value)));
            else if (line.startsWith(FLOAT_VARIABLE)) return String.valueOf(Math.abs(getFloat(value)));
            else if (line.startsWith(DOUBLE_VARIABLE)) return String.valueOf(Math.abs(getDouble(value)));
            else return null;
        } catch (VariableException e) {
            return null;
        }
    }
}
