package bin.parser.param.variable;

import bin.apply.mode.TypeMode;
import bin.parser.param.ParamToken;
import bin.parser.param.ParserParamItem;
import bin.parser.param.ParserParamTool;
import bin.token.EditToken;
import bin.token.Token;
import bin.token.check.CheckToken;
import bin.variable.custom.CustomList;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class ParamArrayListItem extends ParamToken {
    private final ParamToken[] TOKENS;
    private String TYPE = null;

    public ParamArrayListItem(String line) {
        List<ParamToken> list = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(EditToken.bothCut(line), Token.COMMA);
        while (tokenizer.hasMoreTokens()) list.add(new ParserParamItem(tokenizer.nextToken().strip()).start());
        this.TOKENS = list.toArray(ParamToken[]::new);
    }

    @Override
    public Object replace() {
        setType();
        return new CustomList<>(TypeMode.get(TYPE)) {{
            for (ParamToken token : TOKENS) add(token.replace());
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
