package bin.apply.sys.item;

import bin.apply.Repository;
import bin.exception.VariableException;
import bin.token.LoopToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bin.token.Token.*;

public class HpMap<V> extends HashMap<String, V> implements Map<String, V> {
    private final Map<String, Integer> hp = new HashMap<>();
    private final Pattern pattern = Pattern.compile(START + BL + "\\d+" + BR);
    private static final int noCount = -1;

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
    public V get(Object key) {
        V v = super.get(key);
        int hpCount = hp.getOrDefault(key.toString(), 0);
        if (hpCount != noCount) {
            if (--hpCount == 0) {
                super.remove(key);
                hp.remove(key.toString());
            } else hp.put(key.toString(), hpCount);
        }
        return v;
    }

    @Nullable
    @Override
    public V put(String key, V value) {
        Matcher matcher = pattern.matcher(key);
        if (matcher.find()) {
            String group = matcher.group();
            int len = group.length();
            int count = Integer.parseInt(group.substring(1, len-1));
            if (count != 0) key = key.substring(len);
        }
        hp.put(key, noCount);
        return super.put(key, value);
    }

    @Override
    public V remove(Object key) {
        hp.remove(key);
        return super.remove(key);
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends V> m) {
        for (var ms : m.entrySet()) {
            String key = ms.getKey();
            V value = ms.getValue();
            put(key, value);
        }
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
    public Collection<V> values() {
        return super.values();
    }

    @Override
    public Set<Entry<String, V>> entrySet() {
        return super.entrySet();
    }
}
