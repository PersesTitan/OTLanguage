package bin.apply.sys.item;

import bin.check.VariableType;
import bin.check.VariableTypeCheck;
import bin.exception.VariableException;
import bin.token.VariableToken;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static bin.apply.Repository.noUse;
import static bin.check.VariableTypeCheck.getVariableType;
import static bin.check.VariableTypeCheck.originList;
import static bin.token.cal.BoolToken.*;

public class HpMap extends HashMap<String, Object> implements Map<String, Object>, VariableToken {
    private final Map<String, Integer> hp = new HashMap<>();
    private static final int noCount = -1;
    private final VariableType variableType;

    public HpMap(String variableType) {
        this.variableType = getVariableType(variableType);
    }

    @Override
    public int size() {
        return super.size();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        String k = key.toString();
        if (k.startsWith("["))
            k = k.substring(k.indexOf("]") + 1);
        return super.containsKey(k);
    }

    @Override
    public boolean containsValue(Object value) {
        return super.containsValue(value);
    }

    @Override
    public Object getOrDefault(Object key, Object defaultValue) {
        return super.getOrDefault(key, defaultValue);
    }

    @Override
    public Object get(Object key) {
        Object object = super.get(key);
        int hpCount = hp.getOrDefault(key.toString(), -1);
        if (hpCount != noCount) {
            if (--hpCount == 0) remove(key);
            else hp.put(key.toString(), hpCount);
        }
        return object;
    }

    @Override
    public Object put(String key, Object value) {
        int c = 0;
        if (!key.matches(VARIABLE_NAME)) throw new VariableException().variableNameMatch();
        else if (key.startsWith("[")) {
            int right = key.indexOf("]");
            c = Integer.parseInt(key.substring(1, right));
            key = key.substring(right + 1);
        }
        if (c != 0) hp.put(key, c);

        // 예약어 확인
        if (noUse.contains(key)) throw new VariableException().reservedWorks();
        // 사용 불가 단어
        else if (key.contains(OR) || key.contains(AND)
                || key.contains(NOT) || key.contains(TRUE) || key.contains(FALSE))
            throw new VariableException().cannotInclude();

        Object keyObj = this.getOrDefault(key, null);
        Object valueObj = VariableTypeCheck.getInstance().getObject(variableType, value, keyObj);
        if (keyObj == null || originList.contains(variableType)) return super.put(key, valueObj);
        else if (variableType.equals(VariableType.Method)) return super.put(key, valueObj);
        else return value;
    }

    @Override
    public Object remove(Object key) {
        hp.remove(key);
        return super.remove(key);
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ?> m) {
        for (var ms : m.entrySet()) put(ms.getKey(), ms.getValue());
    }

    @Override
    public void clear() {
        hp.clear();
        super.clear();
    }

    @Override
    public Set<String> keySet() {
        return super.keySet();
    }

    @Override
    public Collection<Object> values() {
        return super.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return super.entrySet();
    }
}
