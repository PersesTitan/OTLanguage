package bin.apply.repository.method;

import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.check.CheckToken;
import work.MethodWork;

import java.util.HashMap;

class CountMap extends HashMap<Integer, MethodWork> {
    public CountMap(MethodWork work) {
        this.put(work);
    }

    public void put(MethodWork work) {
        int size = work.getSIZE();
        if (size == 1 && CheckToken.isBlankType(work.getPARAMS()[0])) put(0, work);
        this.put(size, work);
    }

    private void put(int i, MethodWork work) {
        if (super.containsKey(i)) throw VariableException.DEFINE_NAME.getThrow(null);
        else super.put(i, work);
    }

    public MethodWork get(int size) {
        if (super.containsKey(size)) return super.get(size);
        else throw MatchException.PARAM_COUNT_ERROR.getThrow(size);
    }

    public boolean contains(MethodWork work) {
        int size = work.getSIZE();
        if (size == 1 && CheckToken.isBlankType(work.getPARAMS()[0])) {
            return super.containsKey(0) || super.containsKey(1);
        } else return super.containsKey(size);
    }
}
