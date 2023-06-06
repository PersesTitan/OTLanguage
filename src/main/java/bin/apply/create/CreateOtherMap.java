package bin.apply.create;

import bin.exception.VariableException;
import bin.token.KlassToken;
import bin.token.check.CheckToken;
import bin.variable.OtherMap;
import work.CreateWork;

public class CreateOtherMap extends CreateWork<OtherMap<?, ?>> {
    public CreateOtherMap() {
        super((Class<OtherMap<?, ?>>) new OtherMap<>(null, null, null, null).getClass(),
                KlassToken.STRING_VARIABLE, KlassToken.STRING_VARIABLE);
    }

    @Override
    protected OtherMap<?, ?> createItem(Object[] params) {
        String key = params[0].toString();
        String value = params[1].toString();
        if (!CheckToken.isKlass(key)) throw VariableException.TYPE_ERROR.getThrow(key);
        if (!CheckToken.isKlass(value)) throw VariableException.TYPE_ERROR.getThrow(value);
        return new OtherMap<>(key, value);
    }
}
