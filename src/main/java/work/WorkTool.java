package work;

import bin.exception.FileException;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.parser.param.ParamToken;
import bin.token.check.CheckToken;
import bin.token.KlassToken;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public interface WorkTool extends Serializable {
    int getSIZE();
    boolean isSTATIC();
    String[] getPARAMS();
    CreateWork<?> getWORK();
    void reset();

    @Serial
    private void readObject(ObjectInputStream ois) {
        try {
            ois.defaultReadObject();
            reset();
        } catch (IOException | ClassNotFoundException e) {
            throw FileException.DO_NOT_INCLUDE.getThrow(e.getMessage());
        }
    }

    default Object[] casting(ParamToken[] param) {
        String[] types = getPARAMS();
        final int size = param.length;
        final int typeSize = types.length;
        if (size != typeSize) {
            if (size == 0 && typeSize == 1) {
                return switch (getPARAMS()[0]) {
                    case KlassToken.STRING_VARIABLE -> new Object[] {""};
                    case KlassToken.CHARACTER_VARIABLE -> new Object[] {' '};
                    default -> throw MatchException.PARAM_COUNT_ERROR.getThrow(typeSize + " != " + size);
                };
            } else throw MatchException.PARAM_COUNT_ERROR.getThrow(typeSize + " != " + size);
        }
        Object[] values = new Object[size];
        for (int i = 0; i < size; i++) {
            Object value = param[i].replace();
            if (Objects.equals(types[i], param[i].getReplace())) values[i] = param[i].replace();
            else throw VariableException.TYPE_ERROR.getThrow(value);
        }
        return values;
    }

    default void checkParams(String[] params) {
        for (String param : params) {
            if (!CheckToken.isKlass(param)) throw VariableException.NO_DEFINE_TYPE.getThrow(param);
        }
    }

    default Object getKlassItem(Object klassItem) {
        if (isSTATIC()) {
            if (klassItem != null) throw VariableException.ACCESS_ERROR.getThrow(klassItem);
            else return null;
        } else {
            if (klassItem == null) throw VariableException.ACCESS_ERROR.getThrow(null);
            else if (getWORK().check(klassItem)) return klassItem;
            else throw VariableException.VALUE_ERROR.getThrow(klassItem);
        }
    }
}
