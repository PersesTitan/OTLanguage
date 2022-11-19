package etc.v3;

import bin.calculator.tool.Calculator;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

public class BoolTest extends StartWorkV3 implements Calculator {
    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        System.out.println(Calculator.getBool(params[0], repositoryArray));
    }
}
