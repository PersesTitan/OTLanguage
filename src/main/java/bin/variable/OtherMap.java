package bin.variable;

import bin.apply.Repository;
import bin.exception.VariableException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import work.CreateWork;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class OtherMap<K, V> extends LinkedHashMap<K, V> {
    private final CreateWork<K> key;
    private final CreateWork<V> value;
    private final String keyType, valueType;

    public OtherMap(String key, String value) {
        this.key = (CreateWork<K>) Repository.createWorks.get(key);
        this.value = (CreateWork<V>) Repository.createWorks.get(value);
        this.keyType = key;
        this.valueType = value;
    }

    @Override
    public boolean containsKey(Object key) {
        if (this.key.check(key)) return super.containsKey(key);
        else throw VariableException.VALUE_ERROR.getThrow(key);
    }

    @Override
    public boolean containsValue(Object value) {
        if (this.value.check(value)) return super.containsValue(value);
        else throw VariableException.VALUE_ERROR.getThrow(value);
    }

    @Override
    public V get(Object key) {
        if (this.key.check(key)) {
            if (super.containsKey(key)) return super.get(key);
            else throw VariableException.ACCESS_ERROR.getThrow(key);
        } else throw VariableException.TYPE_ERROR.getThrow(key);
    }

    public void add(Object value) {
        if (value instanceof OtherMap<?,?> map && map.key == this.key && map.value == this.value)
            super.putAll((Map<? extends K, ? extends V>) map);
        else throw VariableException.TYPE_ERROR.getThrow(value);
    }
}
