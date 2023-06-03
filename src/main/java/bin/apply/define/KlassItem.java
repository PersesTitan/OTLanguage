package bin.apply.define;

import bin.apply.Repository;
import bin.apply.item.ParamItem;
import bin.apply.repository.variable.TypeMap;
import bin.exception.MatchException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class KlassItem {
    private final TypeMap repository = new TypeMap();
    private final String klassName;

    public KlassItem(String klassName, ParamItem[] items, Object[] values) {
        int size = items.length;
        if (size != values.length) throw MatchException.PARAM_COUNT_ERROR.getThrow(Arrays.toString(values));
        for (int i = 0; i<size; i++) this.repository.create(items[i], values[i]);
        this.klassName = klassName;
    }

    public void addRepository() {
        Repository.repositoryArray.addFirst(this.repository);
    }
}
