package bin.apply.repository.method;

import bin.exception.VariableException;
import work.MethodWork;

import java.util.HashMap;

class MethodMap extends HashMap<String, CountMap> {
    public MethodMap(String method, MethodWork work) {
        this.put(method, work);
    }

    public void put(String method, MethodWork work) {
        if (super.containsKey(method)) super.get(method).put(work);
        else super.put(method, new CountMap(work));
    }

    public MethodWork get(String method, int size) {
        if (super.containsKey(method)) return super.get(method).get(size);
        else throw VariableException.NO_METHOD.getThrow(method);
    }

    public boolean contains(String method, MethodWork work) {
        return super.containsKey(method) && super.get(method).contains(work);
    }
}
