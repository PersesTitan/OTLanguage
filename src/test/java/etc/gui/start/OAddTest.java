package etc.gui.start;

import bin.apply.Setting;
import etc.gui.OGUITest;
import etc.gui.setting.RepositoryTest;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

public class OAddTest extends StartWorkV3 implements RepositoryTest {
    // 2
    public OAddTest(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (OGUITest.createGUI) add(item.get(params[0]), item.get(params[1]));
        else Setting.runMessage(line);
    }
}
