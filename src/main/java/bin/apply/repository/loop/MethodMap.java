package bin.apply.repository.loop;

import bin.exception.MatchException;
import work.LoopWork;

import java.util.HashMap;

class MethodMap extends HashMap<String, LoopCountMap> {
    public <T extends LoopCountMap> MethodMap(String method, T map) {
        super.put(method, map);
    }

    public MethodMap(String method, LoopWork work) {
        this.put(method, work);
    }

    public void put(String method, LoopWork work) {
        if (super.containsKey(method)) super.get(method).put(work);
        else super.put(method, new LoopCountMap(work));
    }

    public LoopWork get(String method, int size) {
        if (super.containsKey(method)) return super.get(method).get(size);
        else throw MatchException.PARAM_COUNT_ERROR.getThrow(method);
    }

    public boolean contains(String method, LoopWork work) {
        return super.containsKey(method) && super.get(method).contains(work);
    }
}
