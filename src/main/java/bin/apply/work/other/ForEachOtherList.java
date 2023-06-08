package bin.apply.work.other;

import bin.apply.Repository;
import bin.apply.item.CodesItem;
import bin.apply.item.ParamItem;
import bin.apply.repository.function.OSConsumer;
import bin.exception.VariableException;
import bin.parser.param.ParamToken;
import bin.token.KlassToken;
import bin.token.check.CheckToken;
import bin.variable.OtherList;
import work.LoopWork;

public class ForEachOtherList extends LoopWork {
    public ForEachOtherList() {
        super(KlassToken.LIST, false, 1, null);
    }

    @Override
    protected void loopItem(Object klass, Object[] params, OSConsumer consumer) {
        ((OtherList<?>) klass).forEach(consumer::accept);
    }

    @Override
    public void loop(Object klass, ParamToken[] params, CodesItem CODE, ParamItem[] ITEM) {
        CheckToken.checkParamLength(PUT_SIZE, params.length);
        Object value = getKlassItem(klass);
        String type = ((OtherList<?>) klass).getType();
        for (ParamItem param : ITEM)
            if (!param.type().equals(type)) throw VariableException.TYPE_ERROR.getThrow(param.type());
        Object[] param = casting(params);
        for (ParamItem item : ITEM) Repository.repositoryArray.createLoop(item);
        try {
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
