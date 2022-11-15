package etc.v3;

import bin.calculator.tool.Calculator;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

public class NumberTest extends StartWorkV3 implements Calculator {
    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        System.out.print("Number Str = ");
        System.out.println(getNumberStr(params[0], repositoryArray));
        System.out.print("Number = ");
        System.out.println(getNumber(params[0], repositoryArray));
    }
}
