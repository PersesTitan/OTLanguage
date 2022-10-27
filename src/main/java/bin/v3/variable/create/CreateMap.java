package bin.v3.variable.create;

import bin.token.MergeToken;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.token.Token.BLANKS;
import static bin.token.VariableToken.MAP_ADD;
import static bin.token.VariableToken.VARIABLE_PUT;

public class CreateMap extends StartWorkV3 implements MergeToken {
    public CreateMap(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String[] values = matchSplitError(line, BLANKS, 2);
        String[] tokens = values[1].split(splitNoCutBack(VARIABLE_PUT, MAP_ADD), 2);
        variableDefineError(tokens[0], repositoryArray.get(0));
        repositoryArray.get(0).get(values[0]).put(tokens[0], tokens.length == 2 ? tokens[1] : "");
    }
}
