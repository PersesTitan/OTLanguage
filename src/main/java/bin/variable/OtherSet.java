package bin.variable;

import bin.apply.Repository;
import bin.exception.VariableException;
import bin.parser.param.ParamToken;
import bin.token.Token;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import work.CreateWork;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

@Getter
@RequiredArgsConstructor
public class OtherSet<T> extends LinkedHashSet<T> {
    private final CreateWork<T> work;
    private final String type;

    public OtherSet(String type) {
        this.work = (CreateWork<T>) Repository.createWorks.get(type);
        this.type = type;
    }

    @Override
    public boolean add(Object v) {
        if (work.check(v)) return super.add((T) v);
        else if (v instanceof ParamToken[] pt) return super.add(work.create(pt));
        else if (v instanceof OtherSet<?> set) {
            if (set.work == work) return super.addAll((Collection<? extends T>) set);
            else throw VariableException.TYPE_ERROR.getThrow(set);
        } else throw VariableException.TYPE_ERROR.getThrow(v);
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

    @Override
    public String toString() {
        Iterator<T> it = iterator();
        if (! it.hasNext()) return Character.toString(Token.SET_S) + Token.SET_E;
        StringBuilder sb = new StringBuilder();
        sb.append(Token.SET_S);
        for (;;) {
            T e = it.next();
            if (e instanceof Boolean bool) sb.append(bool ? Token.TRUE : Token.FALSE);
            else sb.append(e);
            if (! it.hasNext()) return sb.append(Token.SET_E).toString();
            sb.append(Token.COMMA).append(' ');
        }
    }
}
