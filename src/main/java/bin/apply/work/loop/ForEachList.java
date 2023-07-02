package bin.apply.work.loop;

import bin.apply.mode.TypeMode;
import bin.apply.OSConsumer;
import bin.variable.custom.CustomList;
import work.LoopWork;

public class ForEachList extends LoopWork {
    public ForEachList(TypeMode mode) {
        super(mode.getList(), false, 1, new String[]{mode.getType()});
    }

    @Override
    protected void loopItem(Object klass, Object[] params, OSConsumer consumer) {
        ((CustomList<?>) klass).forEach(consumer::accept);
    }
}
