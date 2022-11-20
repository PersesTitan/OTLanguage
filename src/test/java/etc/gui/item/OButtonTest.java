package etc.gui.item;

import bin.apply.Setting;
import etc.gui.OGUITest;
import etc.gui.setting.RepositoryTest;
import work.v3.StartWorkV3;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Map;

public class OButtonTest extends StartWorkV3 implements RepositoryTest {
    // 1 = 이름
    // 1    // 2
    public OButtonTest(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (OGUITest.createGUI) {
            if (params.length == 1) item.put(params[0], new JButton());
            else item.put(params[0], new JButton(params[1]));
        } else Setting.runMessage(line);
    }
}
