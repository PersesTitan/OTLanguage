package bin.string;

import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.token.cal.BoolToken.FALSE;
import static bin.token.cal.BoolToken.TRUE;

public class Contains extends ReturnWorkV3 {
    public Contains(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        return params[0].contains(params[1]) ? TRUE : FALSE;
    }
}
