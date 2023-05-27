package bin.apply.define;

import bin.apply.item.CodesItem;
import bin.apply.item.ParamItem;
import work.CreateWork;

import java.util.Objects;

public class DefineKlass extends CreateWork<KlassItem> {
    private final ParamItem[] PARAMS;
    private final CodesItem CODE;
    private final String NAME;

    public DefineKlass(String NAME, CodesItem CODE, ParamItem[] PARAMS) {
        super(KlassItem.class, ParamItem.getType(PARAMS));
        this.NAME = NAME;
        this.CODE = CODE;
        this.PARAMS = PARAMS;
    }

    @Override
    protected KlassItem createItem(Object[] params) {
        this.CODE.start();
        return new KlassItem(NAME, PARAMS, params);
    }

    @Override
    public boolean check(Object value) {
        return value instanceof KlassItem item
                && Objects.equals(item.getKlassName(), this.NAME);
    }
}
