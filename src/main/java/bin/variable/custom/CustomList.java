package bin.variable.custom;

import bin.apply.mode.TypeMode;
import bin.exception.VariableException;
import bin.token.Token;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.hadoop.hbase.filter.SkipFilter;

import java.io.Serial;
import java.util.*;

import static bin.token.Token.FALSE;
import static bin.token.Token.TRUE;

@Getter
@RequiredArgsConstructor
public class CustomList<V> extends LinkedList<V> implements CustomTool {
    @Serial
    private static final long serialVersionUID = 1193441885283992919L;
    private final TypeMode types;

    @SafeVarargs
    public CustomList(V... vs) {
        this.types = TypeMode.get(vs[0]);
        super.addAll(List.of(vs));
    }

    @SafeVarargs
    public CustomList(TypeMode types, V... vs) {
        this(types);
        for (V v : vs) types.check(v);
        Collections.addAll(this, vs);
    }

    public CustomList(TypeMode types, Collection<V> collection) {
        super(collection);
        this.types = types;
    }

    public CustomList(CustomList<V> list) {
        this.types = list.types;
        super.addAll(list);
    }

    @Override
    public boolean add(Object v) {
        if (types.check(v)) return super.add((V) v);
        else if (v instanceof CustomList<?> list && list.types == types) {
            return super.addAll((Collection<? extends V>) list);
        } else throw VariableException.TYPE_ERROR.getThrow(v);
    }

    public V get(int index) {
        try {
            return super.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw VariableException.ACCESS_ERROR.getThrow(e.getMessage());
        }
    }

    public Object sum() {
        return sum(types, super.stream(), types.getList());
    }

    public Object max() {
        return max(types, super.stream(), types.getList());
    }

    public Object min() {
        return min(types, super.stream(), types.getList());
    }

    @Override
    public String toString() {
        Iterator<V> it = iterator();
        if (! it.hasNext()) return Character.toString(Token.LIST_S) + Token.LIST_E;

        StringBuilder sb = new StringBuilder();
        sb.append(Token.LIST_S);
        for (;;) {
            V e = it.next();
            if (e instanceof Boolean bool) sb.append(bool ? TRUE : FALSE);
            else sb.append(e);
            if (! it.hasNext()) return sb.append(Token.LIST_E).toString();
            sb.append(Token.COMMA).append(' ');
        }
    }
}
