package cos.shell;

import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;

public class ReturnShell extends ReturnWorkV3 {
    public ReturnShell(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params, LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        return null;
    }
}
