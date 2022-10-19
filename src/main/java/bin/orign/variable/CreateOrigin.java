package bin.orign.variable;

import bin.token.LoopToken;
import bin.token.MergeToken;
import work.StartWork;

import java.util.Map;

public class CreateOrigin implements StartWork, LoopToken {

    @Override
    public boolean check(String line) {
        return false;
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {
//        String
        // 변수명:1234  =>
        String[] tokens = matchSplitError(line, VARIABLE_PUT, 2);
        variableDefineError(tokens[0], repositoryArray[0]);
        repositoryArray[0].get()
    }
}
