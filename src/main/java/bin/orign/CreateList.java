package bin.orign;

import bin.apply.Setting;
import bin.token.LoopToken;
import work.StartWork;

import java.util.Arrays;
import java.util.Map;

public class CreateList implements StartWork, LoopToken {

    @Override
    public boolean check(String line) {
        return false;
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {
        String[] values = matchSplitError(line, BLANKS, 2);
        // 변수명<<1234  => ㅁㄴㅇㄹ
        if (!values[0].matches(VARIABLE_NAME)) {
            Setting.runMessage(line);
            return;
        }
        String[] tokens = values[1].split(splitNoCutBack(VARIABLE_PUT, LIST_ADD), 2);
        variableDefineError(tokens[0], repositoryArray[0]);
        repositoryArray[0].get(values[0]).put(tokens[0], tokens.length == 2 ? tokens[1] : "");
    }

    @Override
    public void first() {

    }
}
