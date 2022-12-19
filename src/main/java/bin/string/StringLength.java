package bin.string;

import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;

public class StringLength extends ReturnWorkV3 {
    public StringLength() {
        super(1);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        return Integer.toString(params[0].length());
    }
}
