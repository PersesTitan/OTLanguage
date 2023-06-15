package work;

import bin.apply.Repository;
import bin.exception.VariableException;
import bin.parser.param.ParamToken;
import bin.token.check.CheckToken;
import lombok.Getter;

import java.util.Arrays;

@Getter
public abstract class MethodWork implements WorkTool {
    private final String REPLACE_TYPE;
    private final CreateWork<?> REPLACE;
    private final CreateWork<?> WORK;
    private final boolean STATIC;
    private final String[] PARAMS;
    private final int SIZE;

    public MethodWork(String REPLACE, String TYPE, boolean STATIC, String... PARAMS) {
        if (CheckToken.isKlass(TYPE)) this.WORK = Repository.createWorks.get(TYPE);
        else throw VariableException.NO_DEFINE_TYPE.getThrow(TYPE);
        if (REPLACE == null) this.REPLACE = null;
        else if (CheckToken.isKlass(REPLACE)) this.REPLACE = Repository.createWorks.get(REPLACE);
        else throw VariableException.NO_DEFINE_TYPE.getThrow(REPLACE);
        checkParams(PARAMS);
        this.REPLACE_TYPE = REPLACE;
        this.STATIC = STATIC;
        this.PARAMS = PARAMS;
        this.SIZE = PARAMS.length;
    }

    protected abstract Object methodItem(Object klassItem, Object[] params);
    public Object method(Object klassItem, ParamToken[] param) {
        return methodItem(getKlassItem(klassItem), casting(param));
    }

    @Override
    public void reset() {
        // override used
    }
}
