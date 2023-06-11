package bin.parser.param.variable;

import bin.apply.mode.TypeMode;
import bin.parser.param.ParamToken;
import bin.variable.custom.CustomMap;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ParamMapItem<K, V> extends ParamToken {
    private final TypeMode KEY, VALUE;
    private final ParamToken[] K_PARAM, V_PARAM;
    private final String TYPE;

    @Override
    public Object replace() {
        CustomMap<K, V> map = new CustomMap<>(KEY, VALUE);
        int size = K_PARAM.length;
        for (int i = 0; i<size; i++) map.put(K_PARAM[i].replace(), V_PARAM[i].replace());
        return map;
    }

    @Override
    public String getReplace() {
        return TYPE;
    }
}
