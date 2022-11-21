package etc.gui.start;

import bin.apply.Setting;
import etc.gui.OGUITest;
import etc.gui.setting.RepositoryTest;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.check.VariableCheck.getInteger;

public class OSetSizeTest extends StartWorkV3 implements RepositoryTest {
    // 3
    public OSetSizeTest(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (OGUITest.createGUI) setSize(item.get(params[0]), getInteger(params[1]), getInteger(params[2]));
        else Setting.runMessage(line);
    }
}
