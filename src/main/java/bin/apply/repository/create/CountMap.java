package bin.apply.repository.create;

import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.check.CheckToken;
import work.CreateWork;
import work.MethodWork;

import java.util.HashMap;

public class CountMap extends HashMap<Integer, CreateWork<?>> {
    private final CreateWork<?> work;
    public CountMap(CreateWork<?> work) {
        this.work = work;
        this.put(work);
    }

    public void put(CreateWork<?> work) {
        int size = work.getSIZE();
        if (size == 1 && CheckToken.isBlankType(work.getPARAMS()[0])) put(0, work);
        this.put(size, work);
    }

    private void put(int i, CreateWork<?> work) {
        if (super.containsKey(i)) throw VariableException.DEFINE_NAME.getThrow(null);
        else super.put(i, work);
    }

    public CreateWork<?> get(int size) {
        if (super.containsKey(size)) return super.get(size);
        else throw MatchException.PARAM_COUNT_ERROR.getThrow(size);
    }

    public CreateWork<?> get() {
        return this.work;
    }

    public boolean contains(MethodWork work) {
        int size = work.getSIZE();
        if (size == 1 && CheckToken.isBlankType(work.getPARAMS()[0])) {
            return super.containsKey(0) || super.containsKey(1);
        } else return super.containsKey(size);
    }
}
