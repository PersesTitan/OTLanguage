package bin.apply.calculator.bool;

import bin.parser.param.ParamToken;
import bin.parser.param.ParserParamItem;
import bin.token.CastingToken;
import bin.token.KlassToken;
import bin.token.Token;

public class CalculatorNot extends ParamToken {
    private final ParamToken TOKEN;

    public CalculatorNot(String line) {
        this.TOKEN = new ParserParamItem(line.substring(Token.NOT.length()).strip()).start();
    }

    public static boolean check(String line) {
        return line.length() > 3 && line.startsWith(Token.NOT)
                && Token.BLANKS.contains(line.charAt(2));
    }

    @Override
    public Object replace() {
        return !CastingToken.getBoolean(TOKEN.replace());
    }

    @Override
    public String getReplace() {
        return KlassToken.BOOL_VARIABLE;
    }
}
