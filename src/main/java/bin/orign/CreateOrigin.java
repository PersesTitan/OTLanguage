package bin.orign;

import bin.apply.Setting;
import bin.token.LoopToken;
import bin.token.MergeToken;
import work.StartWork;

import java.util.Arrays;
import java.util.Map;

public class CreateOrigin implements StartWork, LoopToken {

    @Override
    public boolean check(String line) {
        return false;
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {
        // 변수명:1234  => 변수명, 1234
        String[] values = matchSplitError(line, BLANKS, 2);
        if (!values[0].matches(VARIABLE_NAME)) {
            Setting.runMessage(line);
            return;
        }
        String[] tokens = matchSplitError(values[1], VARIABLE_PUT, 2);
        variableDefineError(tokens[0], repositoryArray[0]);
        repositoryArray[0].get(values[0]).put(tokens[0], tokens[1]);
    }

    @Override
    public void first() {

    }
}
