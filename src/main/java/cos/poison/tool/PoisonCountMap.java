package cos.poison.tool;

import bin.apply.repository.loop.LoopCountMap;
import bin.exception.VariableException;
import cos.poison.PoisonMethod;
import cos.poison.type.MethodType;
import lombok.RequiredArgsConstructor;
import work.LoopWork;

@RequiredArgsConstructor
public class PoisonCountMap extends LoopCountMap {
    private final MethodType METHOD;

    @Override
    public void put(LoopWork work) {
        throw VariableException.DEFINE_NAME.getThrow(null);
    }

    @Override
    public LoopWork get(int size) {
        if (super.containsKey(size)) return super.get(size);
        else {
            PoisonMethod map = new PoisonMethod(METHOD, size);
            super.put(size, map);
            return map;
        }
    }

    @Override
    public boolean contains(LoopWork work) {
        return true;
    }
}
