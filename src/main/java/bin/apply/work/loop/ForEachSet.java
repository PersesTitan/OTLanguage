package bin.apply.work.loop;

import bin.apply.item.CodesItem;
import bin.apply.item.ParamItem;
import bin.apply.mode.TypeMode;
import bin.apply.repository.function.OSConsumer;
import bin.parser.param.ParamToken;
import bin.token.check.CheckToken;
import bin.variable.OtherSet;
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
