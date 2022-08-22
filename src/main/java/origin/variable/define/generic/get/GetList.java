package origin.variable.define.generic.get;

import origin.exception.VariableException;
import origin.exception.VariableMessage;
import origin.variable.model.Repository;

import java.util.List;
import java.util.Map;

public interface GetList {
    default List<Object> getArray(String var) {
        for (String key : Repository.repository.keySet()) {
            if (Repository.repository.get(key).containsKey(var))
                return (List<Object>) Repository.repository.get(key).get(var);
        }
        throw new VariableException(var + "이라는 " + VariableMessage.noHaveVarName);
    }

    default List<Object> getArray(String var,
                                 Map<String, Map<String, Object>> repository) {
        for (String key : repository.keySet()) {
            if (repository.get(key).containsKey(var))
                return (List<Object>) repository.get(key).get(var);
        }
        throw new VariableException(var + "이라는 " + VariableMessage.noHaveVarName);
    }
}
