package bin.string.position;

import bin.calculator.tool.Calculator;
import work.v3.ReturnWorkV3;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.check.VariableCheck.getInteger;
import static bin.check.VariableCheck.isInteger;

public class Index extends ReturnWorkV3 implements Calculator {
    // 2, 3
    public Index(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (params.length == 3) {
            String number = getNumberStr(params[2], repositoryArray);
            return isInteger(number)
                    ? Integer.toString(params[0].indexOf(params[1], getInteger(number)))
                    : null;
        } else return Integer.toString(params[0].indexOf(params[1]));
    }
}
