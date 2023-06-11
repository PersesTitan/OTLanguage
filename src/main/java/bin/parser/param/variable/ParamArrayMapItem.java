package bin.parser.param.variable;

import bin.apply.mode.TypeMode;
import bin.exception.MatchException;
import bin.parser.param.ParamToken;
import bin.parser.param.ParserParamItem;
import bin.token.EditToken;
import bin.token.Token;
import bin.token.check.CheckToken;
import bin.variable.custom.CustomList;
import bin.variable.custom.CustomMap;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ParamArrayMapItem extends ParamToken {
    private final String[] KEYS;
    private final ParamToken[] TOKENS;
    private String TYPE = null;

    public ParamArrayMapItem(String line) {
        List<ParamToken> list = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(EditToken.bothCut(line), Token.COMMA);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            int i = token.indexOf('=');
            if (i <= 0) {
                keys.add(line.substring(0, i));
                list.add(new ParserParamItem(line.substring(i + 1)).start());
            } else throw MatchException.GRAMMAR_ERROR.getThrow(line);
        }
        this.TOKENS = list.toArray(ParamToken[]::new);
        this.KEYS = keys.toArray(String[]::new);
    }

    @Override
    public Object replace() {
        setType();
        int size = TOKENS.length;
        return new CustomMap<>(TypeMode.STRING, TypeMode.get(TYPE)) {{
            for (int i = 0; i<size; i++) put(KEYS[i], TOKENS[i].replace());
        }};
    }

    @Override
    public String getReplace() {
        setType();
        return TYPE;
    }

    private void setType() {
        if (TYPE == null) this.TYPE = CheckToken.getType(TOKENS);
    }
}
