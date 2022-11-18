package bin.string.list;

import work.v3.ReturnWorkV3;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.apply.Controller.aggregationList;
import static bin.token.cal.BoolToken.FALSE;
import static bin.token.cal.BoolToken.TRUE;

// 부분 집합
public class ListContains extends ReturnWorkV3 {
    private final String variableType;
    // 2
    public ListContains(String variableType, int... counts) {
        super(counts);
        this.variableType = variableType;
    }

    @Override
    public String start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        LinkedList<Object> list1 = aggregationList.getList2(params[0], this.variableType, repositoryArray);
        LinkedList<Object> list2 = aggregationList.getList2(params[1], this.variableType, repositoryArray);
        return list1.containsAll(list2) ? TRUE : FALSE;
    }
}
