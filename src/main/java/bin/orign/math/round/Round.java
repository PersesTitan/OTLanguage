package bin.orign.math.round;

import bin.calculator.tool.Calculator;
import bin.exception.VariableException;
import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.check.VariableCheck.getDouble;
import static bin.check.VariableCheck.getFloat;
import static bin.token.VariableToken.DOUBLE_VARIABLE;
import static bin.token.VariableToken.FLOAT_VARIABLE;

// double, float
public class Round extends ReturnWorkV3 implements Calculator {
    // 1
    public Round(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String value = getNumberStr(params[0], repositoryArray);

        try {
            if (line.startsWith(FLOAT_VARIABLE)) return String.valueOf(Math.round(getFloat(value)));
            else if (line.startsWith(DOUBLE_VARIABLE)) return String.valueOf(Math.round(getDouble(value)));
            else return null;
        } catch (VariableException e) {
            return null;
        }
    }
}
