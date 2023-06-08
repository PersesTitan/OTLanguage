package bin.apply.work.system;

import bin.exception.VariableException;
import work.CreateWork;

public class CreateSystem extends CreateWork<Object> {
    public CreateSystem() {
        super(Object.class);
    }

    @Override
    protected Object createItem(Object[] params) {
        throw VariableException.NO_DEFINE_NAME.getThrow(null);
    }

    @Override
    public boolean check(Object value) {
        return value != null;
    }
}
