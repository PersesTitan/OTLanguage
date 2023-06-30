package bin.apply.repository.loop;

import bin.exception.MatchException;
import work.LoopWork;

import java.io.Serial;
import java.util.HashMap;

public class LoopMap extends HashMap<String, MethodMap> {
    public <T extends LoopCountMap> void put(String klass, String method, T map) {
        if (super.containsKey(klass)) super.get(klass).put(method, map);
        else super.put(klass, new MethodMap(method, map));
    }

    public void put(String klass, String method, LoopWork work) {
        if (super.containsKey(klass)) super.get(klass).put(method, work);
        else super.put(klass, new MethodMap(method, work));
    }

    public LoopWork get(String klass, String method, int size) {
        if (super.containsKey(klass)) return super.get(klass).get(method, size);
        else throw MatchException.PARAM_COUNT_ERROR.getThrow(klass);
    }
}
