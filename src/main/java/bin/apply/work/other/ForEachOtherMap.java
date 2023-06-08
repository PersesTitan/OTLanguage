package bin.apply.work.other;

import bin.apply.Repository;
import bin.apply.item.CodesItem;
import bin.apply.item.ParamItem;
import bin.apply.repository.function.OSConsumer;
import bin.exception.VariableException;
import bin.parser.param.ParamToken;
import bin.token.KlassToken;
import bin.token.check.CheckToken;
import bin.variable.OtherMap;
import work.LoopWork;

public class ForEachOtherMap extends LoopWork {
    public ForEachOtherMap() {
        super(KlassToken.MAP, false, 2, null);
    }

    @Override
    protected void loopItem(Object klass, Object[] params, OSConsumer consumer) {
        ((OtherMap<?, ?>) klass).forEach(consumer::accept);
    }

    @Override
    public void loop(Object klass, ParamToken[] params, CodesItem CODE, ParamItem[] ITEM) {
        CheckToken.checkParamLength(PUT_SIZE, params.length);
        Object value = getKlassItem(klass);
        OtherMap<?, ?> map = (OtherMap<?, ?>) klass;
        if (ITEM[0].type().equals(map.getKeyType()))
            throw VariableException.TYPE_ERROR.getThrow(ITEM[0].type());
        if (ITEM[1].type().equals(map.getValueType()))
            throw VariableException.TYPE_ERROR.getThrow(ITEM[1].type());
        Object[] param = casting(params);
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
