package bin.v3.string;

import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

import static bin.token.cal.BoolToken.FALSE;
import static bin.token.cal.BoolToken.TRUE;

public class Equals extends ReturnWorkV3 {
    public Equals(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        return Objects.equals(params[0], params[1]) ? TRUE : FALSE;
    }
}
