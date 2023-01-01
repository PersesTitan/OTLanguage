package bin.string;

import bin.calculator.tool.CalculatorTool;
import bin.exception.VariableException;
import com.google.common.math.DoubleMath;
import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;

public class Repeat extends ReturnWorkV3 implements CalculatorTool {
    // 1: str, 2: 반복 횟수
    public Repeat() {
        super(2);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        double number = getDouble(params[1], repositoryArray);
        return params[0].repeat(getCheck(number));
    }

    private int getCheck(double number) {
        if (DoubleMath.isMathematicalInteger(number)) return (int) number;
        else throw new VariableException().typeMatch();
    }
}
