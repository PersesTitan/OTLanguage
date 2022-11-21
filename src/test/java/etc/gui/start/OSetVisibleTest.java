package etc.gui.start;

import bin.apply.Setting;
import bin.calculator.tool.Calculator;
import etc.gui.OGUITest;
import etc.gui.setting.RepositoryTest;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

public class OSetVisibleTest extends StartWorkV3 implements RepositoryTest {
    // 2
    public OSetVisibleTest(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (OGUITest.createGUI) setVisible(item.get(params[0]), Calculator.getBool(params[1], repositoryArray));
        else Setting.runMessage(line);
    }
}
