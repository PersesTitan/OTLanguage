package bin.apply.mode;

import bin.exception.VariableException;
import bin.token.KlassToken;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import work.ResetWork;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum TypeMode {
    INTEGER     (ResetWork.i, ResetWork.si, ResetWork.li, ResetWork.mi, Integer.class),
    LONG        (ResetWork.l, ResetWork.sl, ResetWork.ll, ResetWork.ml, Long.class),
    BOOLEAN     (ResetWork.b, ResetWork.sb, ResetWork.lb, ResetWork.mb, Boolean.class),
    STRING      (ResetWork.s, ResetWork.ss, ResetWork.ls, ResetWork.ms, String.class),
    CHARACTER   (ResetWork.c, ResetWork.sc, ResetWork.lc, ResetWork.mc, Character.class),
    FLOAT       (ResetWork.f, ResetWork.sf, ResetWork.lf, ResetWork.mf, Float.class),
    DOUBLE      (ResetWork.d, ResetWork.sd, ResetWork.ld, ResetWork.md, Double.class);

    private final String type, set, list, map;
    private final Class<?> klass;

    public void checkError(Object value) {
        if (!check(value)) throw VariableException.VALUE_ERROR.getThrow(value);
    }

    public boolean check(Object value) {
        return Objects.equals(klass, value.getClass());
    }

    public static TypeMode get(String type) {
        return switch (type) {
            case KlassToken.INT_VARIABLE -> INTEGER;
            case KlassToken.LONG_VARIABLE -> LONG;
            case KlassToken.BOOL_VARIABLE -> BOOLEAN;
            case KlassToken.STRING_VARIABLE -> STRING;
            case KlassToken.CHARACTER_VARIABLE -> CHARACTER;
            case KlassToken.FLOAT_VARIABLE -> FLOAT;
            case KlassToken.DOUBLE_VARIABLE -> DOUBLE;
            default -> throw VariableException.TYPE_ERROR.getThrow(type);
        };
    }

    public static TypeMode get(Object values) {
        for (TypeMode mode : TypeMode.values()) {
            if (mode.check(values)) return mode;
        }
        throw VariableException.TYPE_ERROR.getThrow(null);
    }
}
