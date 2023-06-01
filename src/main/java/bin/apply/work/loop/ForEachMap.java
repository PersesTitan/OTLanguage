package bin.apply.work.loop;

import bin.apply.mode.TypeMode;
import bin.apply.repository.function.OSConsumer;
import bin.token.KlassToken;
import bin.variable.custom.CustomMap;
import work.LoopWork;

public class ForEachMap extends LoopWork {
    public ForEachMap(TypeMode mode) {
        super(mode.getMap(), false, 2, new String[]{KlassToken.STRING_VARIABLE, mode.getType()});
    }

    @Override
    protected void loopItem(Object klass, Object[] params, OSConsumer consumer) {
        ((CustomMap<?, ?>) klass).forEach(consumer::accept);
    }
}
