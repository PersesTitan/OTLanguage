package bin.string.tocase;

import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

public class ToLower extends ReturnWorkV3 {
    // 1
    public ToLower(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        return params[0].toLowerCase(Locale.ROOT);
    }
}
