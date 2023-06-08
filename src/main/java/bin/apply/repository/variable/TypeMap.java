package bin.apply.repository.variable;

import bin.apply.item.ParamItem;
import bin.exception.VariableException;
import bin.token.check.CheckToken;

import java.util.HashMap;
import java.util.Map;

public class TypeMap extends HashMap<String, HpMap> {
    public void create(ParamItem paramItem, Object value) {
        String type = paramItem.type();
        String name = paramItem.name();
        if (!super.isEmpty() && contains(name)) throw VariableException.DEFINE_NAME.getThrow(name);
        if (super.containsKey(type)) super.get(type).create(name, value);
        else super.put(type, new HpMap(paramItem, value));
    }

    public void create(ParamItem paramItem, Object value, int HP) {
        String type = paramItem.type();
        String name = paramItem.name();
        if (!super.isEmpty() && contains(name)) throw VariableException.DEFINE_NAME.getThrow(name);
        if (super.containsKey(type)) super.get(type).create(name, value, HP);
        else super.put(type, new HpMap(paramItem, value, HP));
    }

    public void create(String type, String name, Object value) {
        if (!CheckToken.isKlass(type)) throw VariableException.NO_DEFINE_TYPE.getThrow(type);
        if (!super.isEmpty() && contains(name)) throw VariableException.DEFINE_NAME.getThrow(name);
        if (super.containsKey(type)) this.get(type).put(name, value);
        else super.put(type, new HpMap(type, name, value));
    }

    public void create(String type, String name, Object value, int HP) {
        if (!CheckToken.isKlass(type)) throw VariableException.NO_DEFINE_TYPE.getThrow(type);
        if (!super.isEmpty() && contains(name)) throw VariableException.DEFINE_NAME.getThrow(name);
        if (super.containsKey(type)) this.get(type).put(name, value, HP);
        else super.put(type, new HpMap(type, name, value, HP));
    }

    public void createLoop(ParamItem item) {
        if (!super.isEmpty() && contains(item.name())) throw VariableException.DEFINE_NAME.getThrow(item.name());
        if (super.containsKey(item.type())) this.get(item.type()).createLoop(item);
        else super.put(item.type(), new HpMap(item));
    }

    public boolean contains(String name) {
        return super.values().stream().anyMatch(v -> v.containsKey(name));
    }

    public void updateLoop(ParamItem item, Object value) {
        super.get(item.type()).replaceLoop(item.name(), value);
    }

    public void update(String type, String name, Object value) {
        this.get(type).replace(name, value);
    }

    public void update(String name, Object value) {
        for (HpMap map : super.values()) {
            if (map.containsKey(name)) map.put(name, value);
        }
        throw VariableException.NO_CREATE_VARIABLE.getThrow(name);
    }

    public Object find(ParamItem item) {
        return find(item.type(), item.name());
    }

    public Object find(String type, String name) {
        return this.get(type).get(name);
    }

    public Object find(String name) {
        for (HpMap map : super.values()) {
            if (map.containsKey(name)) return map.get(name);
        }
        throw VariableException.NO_CREATE_VARIABLE.getThrow(name);
    }

    public HpMap getMap(String name) {
        for (HpMap map : super.values()) {
            if (map.containsKey(name)) return map;
        }
        throw VariableException.NO_CREATE_VARIABLE.getThrow(name);
    }

    public boolean contains(String type, String name) {
        return super.containsKey(type) && super.get(type).containsKey(name);
    }

    // get Entry
    public Map.Entry<String, Object> getEntry(String name) {
        for (HpMap map : super.values()) if (map.containsKey(name)) return map.getEntry(name);
        throw VariableException.NO_CREATE_VARIABLE.getThrow(name);
    }

    @Override
    public HpMap get(Object key) {
        if (super.containsKey(key)) return super.get(key);
        throw VariableException.NO_CREATE_VARIABLE.getThrow(key);
    }
}
