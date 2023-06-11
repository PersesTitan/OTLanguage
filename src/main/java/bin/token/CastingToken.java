package bin.token;

import bin.exception.VariableException;

public interface CastingToken {
    static int getInt(Object value) {
        if (value instanceof Integer i) return i;
        else throw VariableException.TYPE_ERROR.getThrow(value);
    }

    static long getLong(Object value) {
        if (value instanceof Integer i) return (long) i;
        else if (value instanceof Long l) return l;
        else throw VariableException.TYPE_ERROR.getThrow(value);
    }

    static float getFloat(Object value) {
        if (value instanceof Float f) return f;
        else if (value instanceof Integer i) return (float) i;
        else if (value instanceof Long l) return (float) l;
        else throw VariableException.TYPE_ERROR.getThrow(value);
    }

    static double getDouble(Object value) {
        if (value instanceof Double d) return d;
        else if (value instanceof Integer i) return (double) i;
        else if (value instanceof Long l) return (double) l;
        else if (value instanceof Float f) return (double) f;
        else throw VariableException.VALUE_ERROR.getThrow(value);
    }

    static boolean getBoolean(Object value) {
        if (value instanceof Boolean b) return b;
        else throw VariableException.VALUE_ERROR.getThrow(value);
    }
}
