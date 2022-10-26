package klass.str;

import bin.orign.GetOnlyChangeList;
import klass.ReturnWorkTest;

import java.util.LinkedList;
import java.util.Map;

public class JoinTest extends ReturnWorkTest implements GetOnlyChangeList {
    public JoinTest(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        return String.join(params[0], getList(params[1], repositoryArray.get(0)));
    }
}
