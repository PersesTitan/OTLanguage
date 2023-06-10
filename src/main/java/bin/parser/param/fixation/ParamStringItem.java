package bin.parser.param.fixation;

import bin.parser.param.ParamToken;
import bin.token.EditToken;
import bin.token.KlassToken;

public class ParamStringItem extends ParamToken {
    private final String VALUE;

    public ParamStringItem(String VALUE) {
        this.VALUE = EditToken.bothCut(VALUE, 1, 1);
    }

    @Override
    public Object replace() {
        return VALUE;
    }

    @Override
    public String getReplace() {
        return KlassToken.STRING_VARIABLE;
    }
}
