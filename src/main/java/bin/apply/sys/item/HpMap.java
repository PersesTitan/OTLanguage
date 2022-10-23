package bin.apply.sys.item;

import bin.check.VariableType;
import bin.exception.VariableException;
import bin.token.VariableToken;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static bin.apply.Controller.variableTypeCheck;
import static bin.check.VariableTypeCheck.getVariableType;
import static bin.check.VariableTypeCheck.originList;

public class HpMap extends HashMap<String, Object> implements Map<String, Object>, VariableToken {
    private final Map<String, Integer> hp = new HashMap<>();
//    private final Matcher matcher = Pattern.compile(START + BL + "\\d+" + BR).matcher("");
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
        return super.containsKey(key);
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
            if (--hpCount == 0) {
                super.remove(key);
                hp.remove(key.toString());
            } else hp.put(key.toString(), hpCount);
        }
        return object;
    }

    @Override
    public Object put(String key, Object value) {
        if (!key.matches(VARIABLE_NAME)) throw VariableException.variableNameMatch();
        else if (key.startsWith("[")) {
            String[] match = matchSplitError(key, BR, 2);
            int c = Integer.parseInt(match[0].substring(1));
            if (c != 0) key = match[1];
            hp.put(key, c);
        }
        Object keyObj = this.getOrDefault(key, null);
        Object valueObj = variableTypeCheck.getObject(variableType, value.toString(), keyObj);
        if (keyObj == null || originList.contains(variableType)) return super.put(key, valueObj);
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
