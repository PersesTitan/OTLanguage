package klass.str;

import klass.ReturnWorkTest;

import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

import static bin.token.cal.BoolToken.FALSE;
import static bin.token.cal.BoolToken.TRUE;

public class EqualsTest extends ReturnWorkTest {
    public EqualsTest(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        return Objects.equals(params[0], params[1]) ? TRUE : FALSE;
    }
}
