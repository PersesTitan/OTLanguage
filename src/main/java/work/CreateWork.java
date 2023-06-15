package work;

import bin.exception.SystemException;
import bin.parser.param.ParamToken;
import lombok.Getter;

@Getter
public abstract class CreateWork<T> implements WorkTool {
    private final Class<T> KLASS;
    private final String[] PARAMS;
    private final int SIZE;

    public CreateWork(Class<T> KLASS, String... PARAMS) {
        checkParams(PARAMS);
        this.KLASS = KLASS;
        this.PARAMS = PARAMS;
        this.SIZE = PARAMS.length;
        reset();
    }

    protected abstract T createItem(Object[] params);

    public T create(ParamToken[] params) {
        return createItem(casting(params));
    }

    public boolean check(Object value) {
        return KLASS.equals(value.getClass());
    }

    @Override
    public CreateWork<?> getWORK() {
        return this;
    }

    @Override
    public void reset() {
        // override used
    }

    @Override
    public boolean isSTATIC() {
        throw SystemException.SYSTEM_ERROR.getThrow(null);
    }
}
