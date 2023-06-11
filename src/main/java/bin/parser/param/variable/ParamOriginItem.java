package bin.parser.param.variable;

import bin.parser.param.ParamToken;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ParamOriginItem extends ParamToken {
    private final String TYPE;
    private final Object VALUE;

    @Override
    public Object replace() {
        return VALUE;
    }

    @Override
    public String getReplace() {
        return TYPE;
    }
}
