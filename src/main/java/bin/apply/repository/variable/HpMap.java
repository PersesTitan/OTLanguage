package bin.apply.repository.variable;

import bin.apply.Repository;
import bin.apply.item.ParamItem;
import bin.exception.VariableException;
import bin.parser.param.ParamToken;
import bin.token.Token;
import bin.token.check.CheckToken;
import lombok.Getter;
import work.CreateWork;

import java.util.HashMap;
import java.util.Map;

public class HpMap extends HashMap<String, Object> {
    private Map<Object, Integer> HP = null;
    private final CreateWork<?> WORK;
    @Getter
    private final String TYPE;

    public HpMap(ParamItem paramItem, Object value) {
        this(paramItem.type());
        this.create(paramItem.name(), value);
    }

    public HpMap(ParamItem paramItem, Object value, int HP) {
        this(paramItem.type());
        this.create(paramItem.name(), value, HP);
    }

    public HpMap(String type) {
        this.TYPE = type;
        this.WORK = Repository.createWorks.get(type);
    }

    public HpMap(ParamItem paramItem) {
        this(paramItem.type());
        this.createLoop(paramItem);
    }

    public HpMap(String type, String name, Object value) {
        this(type);
        this.put(name, value);
    }

    public HpMap(String type, String name, Object value, int HP) {
        this(type);
        this.put(name, value, HP);
    }

    public void createLoop(ParamItem paramItem) {
        super.put(paramItem.name(), null);
    }

    public void put(String key, Object value, int HP) {
        if (value == null) throw VariableException.VALUE_ERROR.getThrow(null);
        if (HP == 0) return;
        else if (HP < 0) throw VariableException.VALUE_ERROR.getThrow(HP);
        if (this.HP == null) this.HP = new HashMap<>();
        this.HP.put(key, HP);
        this.put(key, value);
    }

    private Object make(Object value) {
        if (value instanceof ParamToken[] PARAMS) return WORK.create(PARAMS);
        else if (WORK.check(value)) return value;
        else throw VariableException.VALUE_ERROR.getThrow(value);
    }

    public void create(String key, Object value) {
        super.put(key, make(value));
    }

    public void create(String key, Object value, int HP) {
        if (HP == 0) return;
        else if (HP < 0) throw VariableException.VALUE_ERROR.getThrow(HP);
        if (this.HP == null) this.HP = new HashMap<>();
        this.HP.put(key, HP);
        super.put(key, make(value));
    }

    public boolean isHp(String key) {
        return HP == null || HP.containsKey(key);
    }

    @Override
    public Object put(String key, Object value) {
        CheckToken.checkVariableName(key);
        return super.put(key, make(value));
    }

    @Override
    public Object replace(String key, Object value) {
        if (value == null) throw VariableException.VALUE_ERROR.getThrow(null);
        super.replace(key, make(value));
        return null;
    }

    public void replaceLoop(String key, Object value) {
        super.replace(key, make(value));
    }

    @Override
    public Object get(Object name) {
        if (!super.containsKey(name)) throw VariableException.NO_CREATE_VARIABLE.getThrow(name);
        if (HP == null || !HP.containsKey(name)) return super.get(name);
        else {
            int i = HP.get(name);
            if (--i == 0) this.remove(name);
            else HP.replace(name, i);
        }
        return super.get(name);
    }

    public Map.Entry<String, Object> getEntry(String name) {
        for (Entry<String, Object> entry : super.entrySet()) if (entry.getKey().equals(name)) return entry;
        throw VariableException.NO_CREATE_VARIABLE.getThrow(name);
    }

    @Override
    public Object remove(Object key) {
        if (HP != null) HP.remove(key);
        return super.remove(key);
    }
}
