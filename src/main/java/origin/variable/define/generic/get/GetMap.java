package origin.variable.define.generic.get;

import origin.exception.VariableException;
import origin.exception.VariableMessage;
import origin.variable.model.Repository;

import java.util.Map;

public interface GetMap {
    default Map<String, Object> getMap(String var) {
        for (String key : Repository.repository.keySet()) {
            if (Repository.repository.get(key).containsKey(var))
                return (Map<String, Object>) Repository.repository.get(key).get(var);
        }
        throw new VariableException(var + "이라는 " + VariableMessage.noHaveVarName);
    }

    default Map<String, Object> getMap(String var,
                                      Map<String, Map<String, Object>> repository) {
        for (String key : repository.keySet()) {
            if (repository.get(key).containsKey(var))
                return (Map<String, Object>) repository.get(key).get(var);
        }
        throw new VariableException(var + "이라는 " + VariableMessage.noHaveVarName);
    }
}
