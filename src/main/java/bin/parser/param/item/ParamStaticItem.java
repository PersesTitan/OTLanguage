package bin.parser.param.item;

import bin.parser.param.ParamToken;
import lombok.RequiredArgsConstructor;
import work.MethodWork;

@RequiredArgsConstructor
public class ParamStaticItem extends ParamToken {
    private final MethodWork WORK;
    private final ParamToken[] PARAMS;

    @Override
    public Object replace() {
        return WORK.method(null, PARAMS);
    }

    @Override
    public String getReplace() {
        return WORK.getREPLACE_TYPE();
    }
}
