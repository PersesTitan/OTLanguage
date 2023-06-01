package bin.apply.work.system;

import bin.parser.param.ParamToken;
import bin.token.KlassToken;
import work.MethodWork;

import java.util.Objects;

public class Equal extends MethodWork {
    public Equal() {
        super(KlassToken.BOOL_VARIABLE,
                KlassToken.SYSTEM, true,
                KlassToken.SYSTEM, KlassToken.SYSTEM);
    }

    @Override
    protected Object methodItem(Object klassItem, Object[] params) {
        return null;
    }

    @Override
    public Object method(Object klassItem, ParamToken[] param) {
        getKlassItem(klassItem);
        return Objects.equals(param[0].replace(), param[1].replace());
    }
}
