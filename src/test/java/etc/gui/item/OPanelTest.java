package etc.gui.item;

import bin.apply.Setting;
import etc.gui.OGUITest;
import etc.gui.setting.RepositoryTest;
import work.v3.StartWorkV3;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Map;

public class OPanelTest extends StartWorkV3 implements RepositoryTest {
    // 1
    public OPanelTest(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (OGUITest.createGUI) item.put(params[0], new JPanel());
        else Setting.runMessage(line);
    }
}
