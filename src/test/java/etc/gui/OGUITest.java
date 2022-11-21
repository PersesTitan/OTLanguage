package etc.gui;

import cos.input.setting.SetIcon;
import etc.gui.setting.RepositoryTest;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.token.LoopToken.LOOP_SET;

public class OGUITest extends StartWorkV3 implements RepositoryTest, SetIcon {
    public static boolean createGUI = false;
    // 0
    public OGUITest(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        setIcon("icon.otlm");
        LOOP_SET.add(ACTIONS);
        OGUITest.createGUI = true;
    }
}
