package bin.string.pattern;

import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;

public class StringReplaceFirst extends ReturnWorkV3 {
    // 1: String, 2: oldWord, 3: newWord
    public StringReplaceFirst() {
        super(3);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        return params[0].replaceFirst(params[1], params[2]);
    }
}
