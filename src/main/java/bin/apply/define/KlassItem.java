package bin.apply.define;

import bin.apply.Repository;
import bin.apply.item.Item;
import bin.apply.item.ParamItem;
import bin.apply.repository.variable.TypeMap;
import bin.exception.MatchException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class KlassItem implements Item {
    private final TypeMap repository = new TypeMap();
    private final String[] NAMES;
    private final String klassName;

    public KlassItem(String klassName, ParamItem[] items, Object[] values) {
        if (items.length != values.length) throw MatchException.PARAM_COUNT_ERROR.getThrow(Arrays.toString(values));
        this.NAMES = new String[items.length];
        for (int i = 0; i<items.length; i++){
            this.repository.create(items[i], values[i]);
            this.NAMES[i] = items[i].name();
        }
        this.klassName = klassName;
    }

    public void addRepository() {
        Repository.repositoryArray.addFirst(this.repository);
    }

    @Override
    public String toString() {
        Object[] param = new Object[NAMES.length];
        for (int i = 0; i<NAMES.length; i++) param[i] = repository.find(NAMES[i]);
        return toString(klassName, param);
    }
}
