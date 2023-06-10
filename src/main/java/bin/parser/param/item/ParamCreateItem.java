package bin.parser.param.item;

import bin.parser.param.ParamToken;
import work.CreateWork;

public class ParamCreateItem extends ParamToken {
    private final CreateWork<?> WORK;
    private final ParamToken[] PARAMS;
    private final String TYPE;

    public ParamCreateItem(String KLASS, CreateWork<?> WORK, ParamToken[] PARAMS) {
        this.WORK = WORK;
        this.PARAMS = PARAMS;
        TYPE = KLASS;
    }

    @Override
    public Object replace() {
        return WORK.create(PARAMS);
    }

    @Override
    public String getReplace() {
        return TYPE;
    }
}
