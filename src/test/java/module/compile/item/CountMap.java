package module.compile.item;

import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

import static module.compile.CompileTest.usedModel;

@ToString
public class CountMap<V> extends HashMap<String, V> {
    private final String key;
    public CountMap(Map<? extends String, ? extends V> m, String key) {
        super(m);
        this.key = key;
    }

    @Override
    public V get(Object key) {
        if (super.containsKey(key)) usedModel.add(this.key);
        return super.get(key);
    }
}
