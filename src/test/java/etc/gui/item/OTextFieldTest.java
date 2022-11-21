package etc.gui.item;

import bin.apply.Setting;
import bin.calculator.tool.Calculator;
import etc.gui.OGUITest;
import etc.gui.setting.RepositoryTest;
import work.v3.StartWorkV3;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Map;

import static bin.check.VariableCheck.getInteger;

public class OTextFieldTest extends StartWorkV3 implements RepositoryTest {
    // 1 = 이름
    // 1    // 2
    public OTextFieldTest(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (OGUITest.createGUI) {
            if (params.length == 1) item.put(params[0], new JTextField());
            else item.put(params[0], new JTextField(params[1]));
        } else Setting.runMessage(line);
    }
}
