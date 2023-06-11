package bin.variable;

import bin.apply.Repository;
import bin.exception.VariableException;
import bin.parser.param.ParamToken;
import bin.token.CastingToken;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import work.CreateWork;

import java.util.Collection;
import java.util.LinkedList;

@Getter
@RequiredArgsConstructor
public class OtherList<V> extends LinkedList<V> {
    private final CreateWork<V> work;
    private final String type;

    public OtherList(String type) {
        this.work = (CreateWork<V>) Repository.createWorks.get(type);
        this.type = type;
    }

    @Override
    public boolean add(Object v) {
        if (work.check(v)) return super.add((V) v);
        else if (v instanceof ParamToken[] pt) return super.add(work.create(pt));
        else if (v instanceof OtherList<?> list) {
            if (list.work == work) return super.addAll((Collection<? extends V>) list);
            else throw VariableException.TYPE_ERROR.getThrow(list);
        } else throw VariableException.TYPE_ERROR.getThrow(v);
    }

    public V get(int index) {
        try {
            return super.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw VariableException.ACCESS_ERROR.getThrow(e.getMessage());
        }
    }
}
