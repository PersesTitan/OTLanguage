package work;

public abstract class StartWork extends MethodWork {
    public StartWork(String TYPE, boolean IS_STATIC, String... PARAMS) {
        super(null, TYPE, IS_STATIC, PARAMS);
    }

    protected abstract void startItem(Object klassItem, Object[] params);

    @Override
    protected Object methodItem(Object klassItem, Object[] params) {
        this.startItem(klassItem, params);
        return null;
    }
}
