package bin.v3.sys;

import bin.exception.VariableException;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.check.VariableCheck.isLong;

public class Sleep extends StartWorkV3 {
    // 1
    public Sleep(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (!isLong(params[0])) throw VariableException.typeMatch();
        long time = Long.parseLong(params[0]);
        try {
            Thread.sleep(time);
        } catch (InterruptedException ignored) {}
    }
}
