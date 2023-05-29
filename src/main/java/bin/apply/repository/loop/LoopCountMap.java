package bin.apply.repository.loop;

import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.check.CheckToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import work.LoopWork;

import java.util.HashMap;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoopCountMap extends HashMap<Integer, LoopWork> {
    public LoopCountMap(LoopWork work) {
        this.put(work);
    }

    public void put(LoopWork work) {
        int size = work.getSIZE();
        if (size == 1 && CheckToken.isBlankType(work.getPARAMS()[0])) put(0, work);
        this.put(size, work);
    }

    private void put(int i, LoopWork work) {
        if (super.containsKey(i)) throw VariableException.DEFINE_NAME.getThrow(null);
        else super.put(i, work);
    }

    public LoopWork get(int size) {
        if (super.containsKey(size)) return super.get(size);
        else throw MatchException.PARAM_COUNT_ERROR.getThrow(size);
    }

    public boolean contains(LoopWork work) {
        int size = work.getSIZE();
        if (size == 1 && CheckToken.isBlankType(work.getPARAMS()[0])) {
            return super.containsKey(0) || super.containsKey(1);
        } else return super.containsKey(size);
    }
}
