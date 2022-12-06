package v4;

import bin.exception.VariableException;

import java.util.HashMap;
import java.util.Map;

public class CastMap extends HashMap<String, HashMap<String, Object>> {
    private boolean contains(String key) {
        for (HashMap<String, Object> map : super.values()) {
            if (map.containsKey(key)) return true;
        }
        return false;
    }

    private void create(String klassName, String methodName, Object value) {
        if (super.containsKey(klassName)) {
            var rep = super.get(klassName);
            if (rep.containsKey(methodName)) throw new VariableException().sameVariable();
            else rep.put(klassName, methodName);
        } else super.put(klassName, new HashMap<>() {{
                put(methodName, value);
            }});
    }
}
