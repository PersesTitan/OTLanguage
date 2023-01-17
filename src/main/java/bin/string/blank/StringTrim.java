package bin.string.blank;

import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;

public class StringTrim extends ReturnWorkV3 {
    // 1: delete blank (" ")
    public StringTrim() {
        super(1);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        return params[0].strip();
    }
}
