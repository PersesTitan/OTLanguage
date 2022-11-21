package etc.gui.start;

import bin.calculator.tool.Calculator;
import etc.gui.setting.RepositoryTest;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.check.VariableCheck.getInteger;

public class OSetColumnsTest extends StartWorkV3 implements RepositoryTest, Calculator {
    // 2
    public OSetColumnsTest(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String number = getNumberStr(params[1], repositoryArray);
        setColumns(item.get(params[0]), getInteger(number));
    }
}
