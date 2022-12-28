package v4;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public enum BigType {
    ORIGIN,
    SET,
    LIST,
    MAP;

    BigType() {

    }

    public static BigType getType(Object value) {
        if (value instanceof LinkedHashSet) return SET;
        else if (value instanceof LinkedList) return LIST;
        else if (value instanceof LinkedHashMap) return MAP;
        else return ORIGIN;
    }
}
