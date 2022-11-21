package etc.gui.replace;

import etc.gui.OGUITest;
import etc.gui.setting.RepositoryTest;
import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;

public class OGetXTest extends ReturnWorkV3 implements RepositoryTest {
    // 1
    public OGetXTest(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        return OGUITest.createGUI
                ? getX(item.get(params[0]))
                : null;
    }
}
