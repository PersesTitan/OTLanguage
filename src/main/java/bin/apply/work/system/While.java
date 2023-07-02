package bin.apply.work.system;

import bin.apply.item.CodesItem;
import bin.apply.item.ParamItem;
import bin.apply.OSConsumer;
import bin.parser.param.ParamToken;
import bin.token.CastingToken;
import bin.token.KlassToken;
import work.LoopWork;

public class While extends LoopWork {
    public While() {
        super(KlassToken.SYSTEM, true, KlassToken.BOOL_VARIABLE);
    }

    @Override
    protected void loopItem(Object klass, Object[] params, OSConsumer consumer) {}

    @Override
    public void loop(Object klass, ParamToken[] params, CodesItem CODE, ParamItem[] ITEM) {
        this.checkType(ITEM);
        this.getKlassItem(klass);
        while (CastingToken.getBoolean(this.casting(params)[0])) CODE.start();
    }
}
