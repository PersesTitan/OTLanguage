package bin.apply.work.loop;

import bin.apply.Repository;
import bin.apply.item.CodesItem;
import bin.apply.item.ParamItem;
import bin.apply.mode.TypeMode;
import bin.apply.repository.function.OSConsumer;
import bin.exception.VariableException;
import bin.parser.param.ParamToken;
import bin.token.KlassToken;
import bin.token.check.CheckToken;
import bin.variable.OtherMap;
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

    @Override
    public void loop(Object klass, ParamToken[] params, CodesItem CODE, ParamItem[] ITEM) {
        CheckToken.checkParamLength(PUT_SIZE, params.length);
        Object value = getKlassItem(klass);
        CustomMap<?, ?> map = (CustomMap<?, ?>) klass;
        if (map.getKeyKlass().getType().equals(ITEM[0].type()))
            throw VariableException.TYPE_ERROR.getThrow(ITEM[0].type());
        if (map.getValueKlass().getType().equals(ITEM[1].type()))
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
