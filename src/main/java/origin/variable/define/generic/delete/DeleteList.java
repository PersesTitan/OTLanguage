package origin.variable.define.generic.delete;

import origin.variable.define.generic.get.GetList;

import java.util.Map;

public interface DeleteList extends GetList {
    //variableName : 변수명
    //index : 삭제할 포지션
    default void deleteList(String variableName, String index,
                           Map<String, Map<String, Object>> repository) {
        int pos = Integer.parseInt(index.replaceAll("[^0-9]", ""));
        getArray(variableName, repository).remove(pos);
    }

    default void deleteList(String variableName, String index) {
        int pos = Integer.parseInt(index.replaceAll("[^0-9]", ""));
        getArray(variableName).remove(pos);
    }
}
