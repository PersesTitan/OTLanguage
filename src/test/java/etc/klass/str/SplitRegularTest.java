package etc.klass.str;

import etc.klass.ReturnWorkTest;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class SplitRegularTest extends ReturnWorkTest {
    public SplitRegularTest(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        // :ㅇㅁㅇ~ㅆㅍㅆ[기본값][자르고 싶은 조건]_
        return Arrays.toString(params[0].split(params[1]));
    }
}
