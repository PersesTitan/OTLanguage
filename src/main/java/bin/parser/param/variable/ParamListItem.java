package bin.parser.param.variable;

import bin.apply.mode.TypeMode;
import bin.parser.param.ParamToken;
import bin.variable.custom.CustomList;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ParamListItem<T> extends ParamToken {
    private final TypeMode TYPE_MODE;
    private final ParamToken[] TOKENS;
    private final String TYPE;

    @Override
    public Object replace() {
        CustomList<T> list = new CustomList<>(TYPE_MODE);
        for (ParamToken token : TOKENS) list.add(token.replace());
        return list;
    }

    @Override
    public String getReplace() {
        return TYPE;
    }
}
