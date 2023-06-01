package bin.apply.work.error;

import bin.token.KlassToken;
import work.CreateWork;

public class CreateThrow extends CreateWork<ThrowItem> {
    public CreateThrow() {
        super(ThrowItem.class, KlassToken.STRING_VARIABLE, KlassToken.STRING_VARIABLE, KlassToken.STRING_VARIABLE);
    }

    @Override
    protected ThrowItem createItem(Object[] params) {
        String big = params[0].toString();
        String message = params[1].toString();
        String sub = params[2].toString();
        return new ThrowItem(big, message, sub);
    }
}
