package bin.string.list;

import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.apply.Controller.aggregationList;

// 교집합
public class ListRetain extends StartWorkV3 {
    private final String variableType;
    // 2
    public ListRetain(String variableType, int... counts) {
        super(counts);
        this.variableType = variableType;
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        LinkedList<Object> list1 = aggregationList.getList1(params[0], this.variableType, repositoryArray);
        LinkedList<Object> list2 = aggregationList.getList2(params[1], this.variableType, repositoryArray);
        list1.retainAll(list2);
    }
}
