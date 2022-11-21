package etc.gui.start;

import bin.apply.Setting;
import bin.exception.MatchException;
import etc.gui.OGUITest;
import etc.gui.setting.RepositoryTest;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

public class OAddActionsTest extends StartWorkV3 implements RepositoryTest {
    // 1
    public OAddActionsTest(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (OGUITest.createGUI) {
            int start = params[0].lastIndexOf('(');
            if (start == -1) throw new MatchException().grammarError();
            String name = params[0].substring(0, start).strip();
            String loop = params[0].substring(start).strip();
            setAction(loop, name, repositoryArray);
        } else Setting.runMessage(line);
    }
}
