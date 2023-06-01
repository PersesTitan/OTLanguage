package bin.apply.work.system;

import bin.exception.SystemException;
import bin.token.KlassToken;
import work.StartWork;

public class Sleep extends StartWork {
    public Sleep() {
        super(KlassToken.SYSTEM, true, KlassToken.LONG_VARIABLE);
    }

    @Override
    protected void startItem(Object klassItem, Object[] params) {
        try {
            Thread.sleep((long) params[0]);
        } catch (InterruptedException e) {
            throw SystemException.RIGHT_ERROR.getThrow(params[0]);
        }
    }
}
