package etc.gui.start;

import bin.apply.Setting;
import etc.gui.OGUITest;
import etc.gui.setting.RepositoryTest;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.check.VariableCheck.getInteger;

public class OSetMoveTest extends StartWorkV3 implements RepositoryTest {
    // 3
    public OSetMoveTest(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (OGUITest.createGUI) {
            int a = getInteger(params[1]);
            int b = getInteger(params[2]);
            setMove(item.get(params[0]), a, b);
        } else Setting.runMessage(line);
    }
}
