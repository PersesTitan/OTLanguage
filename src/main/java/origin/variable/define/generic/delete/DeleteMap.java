package origin.variable.define.generic.delete;

import origin.variable.define.generic.get.GetMap;

import java.util.Map;

public interface DeleteMap extends GetMap {
    //variableName : 변수명
    //key : 삭제할 값
    default void deleteMap(String variableName, String key,
                           Map<String, Map<String, Object>> repository) {
        getMap(variableName, repository).remove(key);
    }

    default void deleteMap(String variableName, String key) {
        getMap(variableName).remove(key);
    }
}
