package cos.shell;

import cos.shell.tool.ShellTool;
import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;

public class ReturnShell extends ReturnWorkV3 implements ShellTool {
    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        return getValue(line, repositoryArray).toString();
    }
}
