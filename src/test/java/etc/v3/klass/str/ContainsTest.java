package etc.v3.klass.str;

import etc.v3.klass.v2.ReturnWorkTest;

import java.util.LinkedList;
import java.util.Map;

import static bin.token.cal.BoolToken.FALSE;
import static bin.token.cal.BoolToken.TRUE;

public class ContainsTest extends ReturnWorkTest {
    public ContainsTest(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        return params[0].contains(params[1]) ? TRUE : FALSE;
    }
}
