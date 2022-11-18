package bin.string.tocase;

import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

public class ToUpper extends ReturnWorkV3 {
    // 1
    public ToUpper(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        return params[0].toUpperCase(Locale.ROOT);
    }
}
