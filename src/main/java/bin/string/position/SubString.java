package bin.string.position;

import bin.calculator.tool.Calculator;
import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.check.VariableCheck.getInteger;

public class SubString extends ReturnWorkV3 implements Calculator {
    // 2, 3
    public SubString(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String number = getNumberStr(params[1], repositoryArray);
        if (params.length == 2) return params[0].substring(getInteger(number));
        else {
            String number1 = getNumberStr(params[2], repositoryArray);
            return params[0].substring(getInteger(number), getInteger(number1));
        }
    }
}
