package bin.orign.variable;

import bin.apply.Setting;
import bin.token.LoopToken;
import work.StartWork;

import java.util.Arrays;
import java.util.Map;

public class CreateSet implements StartWork, LoopToken {
    @Override
    public boolean check(String line) {
        return false;
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {
        String[] values = matchSplitError(line, BLANKS, 2);
        String[] tokens = values[1].split(splitNoCutBack(VARIABLE_PUT, SET_ADD), 2);
        variableDefineError(tokens[0], repositoryArray[0]);
        repositoryArray[0].get(values[0]).put(tokens[0], tokens.length == 2 ? tokens[1] : "");
    }

    @Override
    public void first() {

    }
}
