package origin.variable.define.generic.delete;

import origin.variable.define.generic.get.GetSet;

import java.util.Map;

public interface DeleteSet extends GetSet {
    //variableName : 변수명
    //index : 삭제할 포지션
    default void deleteSet(String variableName, String index,
                           Map<String, Map<String, Object>> repository) {
        getSet(variableName, repository).remove(index);
    }

    default void deleteSet(String variableName, String index) {
        getSet(variableName).remove(index);
    }
}
