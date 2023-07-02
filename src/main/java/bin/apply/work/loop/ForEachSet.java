package bin.apply.work.loop;

import bin.apply.mode.TypeMode;
import bin.apply.OSConsumer;
import bin.variable.custom.CustomSet;
import work.LoopWork;

public class ForEachSet extends LoopWork {
    public ForEachSet(TypeMode mode) {
        super(mode.getSet(), false, 1, new String[]{mode.getType()});
    }

    @Override
    protected void loopItem(Object klass, Object[] params, OSConsumer consumer) {
        ((CustomSet<?>) klass).forEach(consumer::accept);
    }
}
