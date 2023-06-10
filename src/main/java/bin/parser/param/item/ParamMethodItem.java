package bin.parser.param.item;

import bin.apply.Repository;
import bin.parser.param.ParamToken;
import bin.parser.param.ParserParamItem;
import lombok.RequiredArgsConstructor;
import work.MethodWork;

import java.util.Objects;

@RequiredArgsConstructor
public class ParamMethodItem extends ParamToken {
    private final ParamToken TOKEN;
    private final String METHOD;
    private final String[] PARAMS_VALUE;

    private String REPLACE_TYPE = null;
    private ParamToken[] PARAMS = null;
    private MethodWork WORK = null;

    @Override
    public Object replace() {
        setWork();
        return WORK.method(TOKEN.replace(), PARAMS);
    }

    @Override
    public String getReplace() {
        setWork();
        return WORK.getREPLACE_TYPE();
    }

    private void setWork() {
        if (WORK == null || !Objects.equals(REPLACE_TYPE, TOKEN.getReplace())) {
            int size = PARAMS_VALUE.length;
            this.REPLACE_TYPE = TOKEN.getReplace();
            this.WORK = Repository.methodWorks.get(REPLACE_TYPE, METHOD, size);
            this.PARAMS = ParserParamItem.startParam(size, WORK, PARAMS_VALUE);
        }
    }
}
