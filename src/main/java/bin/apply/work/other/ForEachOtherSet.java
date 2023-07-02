package bin.apply.work.other;

import bin.apply.Repository;
import bin.apply.item.CodesItem;
import bin.apply.item.ParamItem;
import bin.apply.OSConsumer;
import bin.exception.VariableException;
import bin.parser.param.ParamToken;
import bin.token.KlassToken;
import bin.token.check.CheckToken;
import bin.variable.OtherSet;
import work.LoopWork;

public class ForEachOtherSet extends LoopWork {
    public ForEachOtherSet() {
        super(KlassToken.SET, false, 1, null);
    }

    @Override
    protected void loopItem(Object klass, Object[] params, OSConsumer consumer) {
        ((OtherSet<?>) klass).forEach(consumer::accept);
    }

    @Override
    public void loop(Object klass, ParamToken[] params, CodesItem CODE, ParamItem[] ITEM) {
        CheckToken.checkParamLength(PUT_SIZE, params.length);
        Object value = getKlassItem(klass);
        String type = ((OtherSet<?>) klass).getType();
        for (ParamItem param : ITEM)
            if (!param.type().equals(type)) throw VariableException.TYPE_ERROR.getThrow(param.type());
        Object[] param = casting(params);
        if (ITEM.length == 0) loopItem(value, param, v -> CODE.start());
        else {
            try {
                for (ParamItem item : ITEM) Repository.repositoryArray.createLoop(item);
                loopItem(value, param, v -> {
                    CheckToken.checkParamLength(ITEM.length, v.length);
                    for (int i = 0; i<ITEM.length; i++) Repository.repositoryArray.updateLoop(ITEM[i], v[i]);
                    CODE.start();
                });
            } finally {
                for (ParamItem item : ITEM) Repository.repositoryArray.remove(item);
            }
        }
    }
}
