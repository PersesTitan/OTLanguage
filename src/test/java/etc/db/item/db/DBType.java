package etc.db.item.db;

import lombok.Getter;

import static bin.token.VariableToken.*;

@Getter
public enum DBType {
    INT(INT_VARIABLE, true, "INT"),
    BIGINT(LONG_VARIABLE, true, "BIGINT"),
    BIT(BOOL_VARIABLE, true, "BIT"),
    FLOAT(FLOAT_VARIABLE, true, "FLOAT"),
    DOUBLE(DOUBLE_VARIABLE, true, "DOUBLE"),
    TEXT(STRING_VARIABLE, false, "TEXT"),
    CHAR(CHARACTER_VARIABLE, false, "CHAR");

    private final String typeName;
    private final boolean isNumber;
    private final String query;
    DBType(String typeName, boolean isNumber, String query) {
        this.typeName = typeName;
        this.isNumber = isNumber;
        this.query = query;
    }
}
