package bin.variable.custom;

import bin.apply.mode.TypeMode;
import bin.exception.VariableException;
import bin.token.Token;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static bin.token.Token.FALSE;
import static bin.token.Token.TRUE;

@Getter
@RequiredArgsConstructor
public class CustomMap<K, V> extends LinkedHashMap<K, V> implements CustomTool {
    @Serial
    private static final long serialVersionUID = -1822639650500173908L;
    private final TypeMode keyKlass;
    private final TypeMode valueKlass;

    public CustomMap(CustomMap<K, V> map) {
        this.keyKlass = map.keyKlass;
        this.valueKlass = map.valueKlass;
        super.putAll(map);
    }

    public void add(Object value) {
        if (value instanceof CustomMap<?,?> map) {
            if (map.keyKlass == keyKlass && map.valueKlass == valueKlass)
                super.putAll((Map<? extends K, ? extends V>) map);
            else throw VariableException.TYPE_ERROR.getThrow(map);
        } else throw VariableException.TYPE_ERROR.getThrow(value);
    }

    @Override
    public boolean containsKey(Object key) {
        keyKlass.checkError(key);
        return super.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        valueKlass.checkError(value);
        return super.containsValue(value);
    }

    @Override
    public V put(Object key, Object value) {
        keyKlass.checkError(key);
        valueKlass.checkError(value);
        return super.put((K) key, (V) value);
    }

    public Object sum() {
        return sum(valueKlass, super.values().stream(), valueKlass.getMap());
    }

    public Object max() {
        return max(valueKlass, super.values().stream(), valueKlass.getMap());
    }

    public Object min() {
        return min(valueKlass, super.values().stream(), valueKlass.getMap());
    }

    @Override
    public V get(Object key) {
        keyKlass.checkError(key);
        if (super.containsKey(key)) return super.get(key);
        else throw VariableException.ACCESS_ERROR.getThrow(key);
    }

    @Override
    public String toString() {
        Iterator<Map.Entry<K,V>> i = entrySet().iterator();
        if (! i.hasNext()) return Character.toString(Token.MAP_S) + Token.MAP_E;

        StringBuilder sb = new StringBuilder();
        sb.append(Token.MAP_S);
        for (;;) {
            Map.Entry<K,V> e = i.next();
            K key = e.getKey();
            V value = e.getValue();
            if (key instanceof Boolean bool) sb.append(bool ? TRUE : FALSE);
            else sb.append(key);
            sb.append('=');
            if (value instanceof Boolean bool) sb.append(bool ? TRUE : FALSE);
            else sb.append(value);
            if (! i.hasNext()) return sb.append(Token.MAP_E).toString();
            sb.append(Token.COMMA).append(' ');
        }
    }
}
