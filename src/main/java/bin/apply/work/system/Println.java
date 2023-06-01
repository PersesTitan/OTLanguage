package bin.apply.work.system;

import bin.parser.param.ParamToken;
import bin.token.EditToken;
import bin.token.KlassToken;
import work.MethodWork;

public class Println extends MethodWork {
    public Println() {
        super(null, KlassToken.SYSTEM, true, KlassToken.STRING_VARIABLE);
    }

    @Override
    protected Object methodItem(Object klassItem, Object[] params) {
        return null;
    }

    @Override
    public Object method(Object klassItem, ParamToken[] param) {
        getKlassItem(klassItem);
        if (param.length == 0) System.out.println();
        else System.out.println(EditToken.toString(param[0].replace()));
        return null;
    }
}
