package bin.apply.create;

import bin.exception.VariableException;
import bin.token.KlassToken;
import bin.token.check.CheckToken;
import bin.variable.OtherList;
import work.CreateWork;

public class CreateOtherList extends CreateWork<OtherList<?>> {
    public CreateOtherList() {
        super((Class<OtherList<?>>) new OtherList<>(null, null).getClass(), KlassToken.STRING_VARIABLE);
    }

    @Override
    protected OtherList<?> createItem(Object[] params) {
        String type = params[0].toString();
        if (CheckToken.isKlass(type)) return new OtherList<>(type);
        else throw VariableException.TYPE_ERROR.getThrow(type);
    }
}
