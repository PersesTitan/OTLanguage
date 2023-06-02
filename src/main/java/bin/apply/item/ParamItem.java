package bin.apply.item;

import bin.exception.VariableException;
import bin.token.check.CheckToken;
import bin.token.EditToken;

public record ParamItem(String type, String name) {
    public ParamItem {
        if (!CheckToken.isKlass(type)) throw VariableException.NO_DEFINE_TYPE.getThrow(type);
        CheckToken.checkVariableName(name);
    }

    public static ParamItem create(String token, char c) {
        String[] tokens = EditToken.split(token, c);
        return new ParamItem(tokens[0], tokens[1]);
    }

    public static String[] getType(ParamItem[] items) {
        int size = items.length;
        String[] param = new String[size];
        for (int i = 0; i<size; i++) param[i] = items[i].type;
        return param;
    }
}
