package etc.gui.item;

import bin.apply.Setting;
import cos.input.setting.SetIcon;
import etc.gui.OGUITest;
import etc.gui.setting.RepositoryTest;
import work.v3.StartWorkV3;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Map;

public class OFrameTest extends StartWorkV3 implements SetIcon, RepositoryTest {
    // 0, 1
    public OFrameTest(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (OGUITest.createGUI) item.put(params[0], new JFrame());
        else Setting.runMessage(line);
    }
}
