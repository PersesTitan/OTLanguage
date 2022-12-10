package bin.apply.sys.compile.items;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ToString
public class CountMap<V> extends HashMap<String, V> {
    private final String key;
    private final Set<String> usedKlass;
    public CountMap(Map<? extends String, ? extends V> m, String key, Set<String> usedKlass) {
        super(m);
        this.usedKlass = usedKlass;
        this.key = key;
    }

    @Override
    public V get(Object key) {
        if (super.containsKey(key)) usedKlass.add(this.key);
        return super.get(key);
    }
}