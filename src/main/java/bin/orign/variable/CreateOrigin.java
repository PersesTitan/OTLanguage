package bin.orign.variable;

import bin.apply.Setting;
import bin.token.LoopToken;
import bin.token.MergeToken;
import work.StartWork;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class CreateOrigin implements StartWork, LoopToken {

    @Override
    public boolean check(String line) {
        return false;
    }

    @Override
    public void start(String line, String origen,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        // 변수명:1234  => 변수명, 1234
        String[] values = matchSplitError(line, BLANKS, 2);
        String[] tokens = values[1].split(VARIABLE_PUT, 2);
        variableDefineError(tokens[0], repositoryArray.get(0));
        repositoryArray.get(0).get(values[0]).put(tokens[0], tokens.length == 2 ? tokens[1] : "");
    }

    @Override
    public void first() {

    }
}
