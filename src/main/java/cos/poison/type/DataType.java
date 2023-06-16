package cos.poison.type;

import cos.poison.PoisonException;

public enum DataType {
    JSON, URL;

    public static DataType getType(String type) {
        try {
            return DataType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw PoisonException.DATA_TYPE_ERROR.getThrow(type);
        }
    }
}
