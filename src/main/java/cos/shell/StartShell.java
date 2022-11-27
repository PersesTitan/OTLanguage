package cos.shell;

import cos.shell.tool.ShellTool;
import work.v3.StartWorkV3;

import java.util.*;

public class StartShell extends StartWorkV3 implements ShellTool {
    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        getValue(line, repositoryArray);
    }
}
