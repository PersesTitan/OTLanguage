package v4.item;

import v4.bin.apply.sys.item.Type;
import v4.bin.apply.sys.item.TypeMap;

import java.util.LinkedList;

// type
public record StartItem(boolean isLoop, String...types) {
    public Object[] checkType(String[] params, LinkedList<TypeMap> repositoryArray) {
        return getValue(params, types, repositoryArray);
    }

    private Object[] getValue(String[] params, String[] types, LinkedList<TypeMap> repositoryArray) {
        Object[] values = new Object[params.length];
        for (int i = 0; i<params.length; i++) {
            values[i] = Type.getType(types[i]).getValue(types[i], params[i], repositoryArray);
        }
        return values;
    }
}
