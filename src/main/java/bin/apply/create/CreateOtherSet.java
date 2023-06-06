package bin.apply.create;

import bin.exception.VariableException;
import bin.token.KlassToken;
import bin.token.check.CheckToken;
import bin.variable.OtherSet;
import work.CreateWork;

public class CreateOtherSet extends CreateWork<OtherSet<?>> {
    public CreateOtherSet() {
        super((Class<OtherSet<?>>) new OtherSet<>(null, null).getClass(),
                KlassToken.STRING_VARIABLE);
    }

    @Override
    protected OtherSet<?> createItem(Object[] params) {
        String type = params[0].toString();
        if (CheckToken.isKlass(type)) return new OtherSet<>(type);
        else throw VariableException.TYPE_ERROR.getThrow(type);
    }
}
