package bin.parser.param.fixation;

import bin.exception.VariableException;
import bin.parser.param.ParamToken;
import bin.token.EditToken;
import bin.token.KlassToken;

public class ParamCharItem extends ParamToken {
    private final char VALUE;

    public ParamCharItem(String VALUE) {
        if (VALUE.length() == 3) throw VariableException.VALUE_ERROR.getThrow(VALUE);
        else this.VALUE = EditToken.bothCut(VALUE, 1, 1).charAt(0);
    }

    @Override
    public Object replace() {
        return VALUE;
    }

    @Override
    public String getReplace() {
        return KlassToken.CHARACTER_VARIABLE;
    }
}
