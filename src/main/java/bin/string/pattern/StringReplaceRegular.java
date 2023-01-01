package bin.string.pattern;

import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;

public class StringReplaceRegular extends ReturnWorkV3 {
    // 1: String, 2: oldWord, 3: newWord
    public StringReplaceRegular() {
        super(3);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        return params[0].replaceAll(params[1], params[2]);
    }
}
