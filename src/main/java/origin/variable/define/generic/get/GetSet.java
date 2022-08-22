package origin.variable.define.generic.get;

import origin.exception.VariableException;
import origin.exception.VariableMessage;
import origin.variable.model.Repository;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public interface GetSet {
    default LinkedHashSet<Object> getSet(String var) {
        for (String key : Repository.repository.keySet()) {
            if (Repository.repository.get(key).containsKey(var))
                return (LinkedHashSet<Object>) Repository.repository.get(key).get(var);
        }
        throw new VariableException(var + "이라는 " + VariableMessage.noHaveVarName);
    }

    default LinkedHashSet<Object> getSet(String var,
                                  Map<String, Map<String, Object>> repository) {
        for (String key : repository.keySet()) {
            if (repository.get(key).containsKey(var))
                return (LinkedHashSet<Object>) repository.get(key).get(var);
        }
        throw new VariableException(var + "이라는 " + VariableMessage.noHaveVarName);
    }
}
