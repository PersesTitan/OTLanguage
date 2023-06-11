package bin.variable.custom;

import bin.apply.mode.TypeMode;
import bin.exception.VariableException;
import bin.token.Token;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

import static bin.token.Token.FALSE;
import static bin.token.Token.TRUE;

@Getter
@RequiredArgsConstructor
public class CustomSet<T> extends LinkedHashSet<T> implements CustomTool {
    @Serial
    private static final long serialVersionUID = 3285786549745547938L;
    private final TypeMode types;

    public CustomSet(CustomSet<T> set) {
        this.types = set.types;
        super.addAll(set);
    }

    @Override
    public boolean contains(Object o) {
        types.checkError(o);
        return super.contains(o);
    }

    @Override
    public boolean add(Object t) {
        if (types.check(t)) return super.add((T) t);
        else if (t instanceof CustomSet<?> set) {
            if (set.types == types) return super.addAll((Collection<? extends T>) set);
            else throw VariableException.TYPE_ERROR.getThrow(set);
        } else throw VariableException.TYPE_ERROR.getThrow(t);
    }

    public T get(int index) {
        int count = 0;
        Iterator<T> iterator = super.iterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            if (count++ == index) return t;
        }
        throw VariableException.ACCESS_ERROR.getThrow(index);
    }

    public Object sum() {
        return sum(types, super.stream(), types.getSet());
    }

    public Object max() {
        return max(types, super.stream(), types.getSet());
    }

    public Object min() {
        return min(types, super.stream(), types.getSet());
    }

    @Override
    public String toString() {
        Iterator<T> it = iterator();
        if (! it.hasNext()) return Character.toString(Token.SET_S) + Token.SET_E;

        StringBuilder sb = new StringBuilder();
        sb.append(Token.SET_S);
        for (;;) {
            T e = it.next();
            if (e instanceof Boolean bool) sb.append(bool ? TRUE : FALSE);
            else sb.append(e);
            if (! it.hasNext()) return sb.append(Token.SET_E).toString();
            sb.append(Token.COMMA).append(' ');
        }
    }
}
