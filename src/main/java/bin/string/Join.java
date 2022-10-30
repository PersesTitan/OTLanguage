package bin.string;

import bin.orign.GetOnlyChangeList;
import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;

public class Join extends ReturnWorkV3 implements GetOnlyChangeList {
    public Join(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        return String.join(params[0], getList(params[1], repositoryArray.get(0)));
    }
}
