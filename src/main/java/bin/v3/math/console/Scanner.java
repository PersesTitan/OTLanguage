package bin.v3.math.console;

import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.apply.Controller.scanner;

public class Scanner extends ReturnWorkV3 {
    public Scanner(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        return scanner();
    }
}
