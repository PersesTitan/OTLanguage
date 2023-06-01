package bin.apply.work.system;

import bin.parser.param.ParamToken;
import bin.token.EditToken;
import bin.token.KlassToken;
import work.MethodWork;

public class Print extends MethodWork {
    private final String PRINT_TOKEN;

    public Print(String PRINT_TOKEN) {
        super(null, KlassToken.SYSTEM, true, KlassToken.STRING_VARIABLE);
        this.PRINT_TOKEN = PRINT_TOKEN;
    }

    @Override
    protected Object methodItem(Object klassItem, Object[] params) {
        return null;
    }

    @Override
    public Object method(Object klassItem, ParamToken[] param) {
        getKlassItem(klassItem);
        if (param.length == 0) System.out.print(PRINT_TOKEN);
        else System.out.print(EditToken.toString(param[0].replace()).concat(PRINT_TOKEN));
        return null;
    }
}
