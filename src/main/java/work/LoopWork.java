package work;

import bin.apply.Repository;
import bin.apply.item.CodesItem;
import bin.apply.item.ParamItem;
import bin.apply.OSConsumer;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.parser.param.ParamToken;
import bin.token.check.CheckToken;
import lombok.Getter;

@Getter
public abstract class LoopWork implements WorkTool {
    private final CreateWork<?> WORK;
    private final boolean STATIC;
    private final String[] PARAMS;
    private final String[] PUT_TYPE; // [ㅇㅁㅇ, ㅇㅈㅇ, ...]
    protected final int PUT_SIZE;
    private final int SIZE;

    // PUT
    public LoopWork(String TYPE, boolean STATIC, int PUT_SIZE, String[] PUT_TYPE, String... PARAMS) {
        if (CheckToken.isKlass(TYPE)) this.WORK = Repository.createWorks.get(TYPE);
        else throw VariableException.NO_DEFINE_TYPE.getThrow(TYPE);
        if (PUT_TYPE != null) checkParams(PUT_TYPE);
        checkParams(PARAMS);
        this.PUT_TYPE = PUT_TYPE;
        this.STATIC = STATIC;
        this.PARAMS = PARAMS;
        this.SIZE = PARAMS.length;
        this.PUT_SIZE = PUT_SIZE;
    }

    public LoopWork(String TYPE, boolean STATIC, String[] PUT_TYPE, String... PARAMS) {
        this(TYPE, STATIC, 1, PUT_TYPE, PARAMS);
    }

    // NONE
    public LoopWork(String TYPE, boolean STATIC, String... PARAMS) {
        this(TYPE, STATIC, 0, null, PARAMS);
    }

    protected abstract void loopItem(Object klass, Object[] params, OSConsumer consumer);
    public void loop(Object klass, ParamToken[] params, CodesItem CODE, ParamItem[] ITEM) {
        this.checkType(ITEM);
        Object value = getKlassItem(klass);
        Object[] param = casting(params);
        if (ITEM.length == 0) loopItem(value, param, v -> CODE.start());
        else try {
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

    protected void checkType(ParamItem[] params) {
        if (params.length == 0) return;
        else if (PUT_TYPE == null) throw MatchException.GRAMMAR_ERROR.getThrow(null);
        else CheckToken.checkParamLength(PUT_SIZE, params.length);
        for (ParamItem param : params)
            if (!isPutType(param.type())) throw VariableException.TYPE_ERROR.getThrow(param.type());
    }

    protected boolean isPutType(String type) {
        for (String put : PUT_TYPE) if (type.equals(put)) return true;
        return false;
    }

    @Override
    public void reset() {}
}
