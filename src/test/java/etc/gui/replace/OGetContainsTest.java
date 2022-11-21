package etc.gui.replace;

import etc.gui.OGUITest;
import etc.gui.setting.RepositoryTest;
import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.check.VariableCheck.getInteger;

public class OGetContainsTest extends ReturnWorkV3 implements RepositoryTest {
    // 3
    public OGetContainsTest(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (OGUITest.createGUI) {
            int a = getInteger(params[1]);
            int b = getInteger(params[2]);
            return getContains(item.get(params[0]), a, b);
        } else return null;
    }
}
