package bin.string.with;

import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.token.cal.BoolToken.FALSE;
import static bin.token.cal.BoolToken.TRUE;

public class EndsWith extends ReturnWorkV3 {
    // 1 : string value // 2 : end
    public EndsWith() {
        super(2);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        return params[0].endsWith(params[1]) ? TRUE : FALSE;
    }
}
