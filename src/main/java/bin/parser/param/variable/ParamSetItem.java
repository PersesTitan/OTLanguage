package bin.parser.param.variable;

import bin.apply.mode.TypeMode;
import bin.parser.param.ParamToken;
import bin.token.EditToken;
import bin.variable.custom.CustomSet;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ParamSetItem<T> extends ParamToken {
    private final TypeMode TYPE_MODE;
    private final ParamToken[] TOKENS;
    private final String TYPE;

    @Override
    public Object replace() {
        CustomSet<T> set = new CustomSet<>(TYPE_MODE);
        for (ParamToken TOKEN : TOKENS) set.add(TOKEN.replace());
        return set;
    }

    @Override
    public String getReplace() {
        return TYPE;
    }
}
