package bin.apply.calculator.bool;

import bin.exception.MatchException;
import bin.parser.param.ParamToken;
import bin.parser.param.ParserParamItem;
import bin.token.CastingToken;
import bin.token.KlassToken;
import bin.token.Token;

import java.util.function.BiFunction;

public class CalculatorCompare extends ParamToken implements CompareTool {
    private final BiFunction<Double, Double, Boolean> FUNCTION;
    private final ParamToken A, B;

    public CalculatorCompare(String line) {
        int s = compare(line), e = line.indexOf(Token.TOKEN, s+1) + 1;
        this.A = new ParserParamItem(line.substring(0, s).strip()).start();
        this.B = new ParserParamItem(line.substring(e).strip()).start();
        String token = line.substring(s+1, e-1);
        this.FUNCTION = switch (token.length()) {
            case 1 -> switch (token.charAt(0)) {
                case GT -> GT_FUNCTION;
                case LT -> LT_FUNCTION;
                case EQ -> EQ_FUNCTION;
                case NQ -> NQ_FUNCTION;
                default -> throw MatchException.GRAMMAR_ERROR.getThrow(null);
            };
            case 2 -> {
                if (token.equals(GE)) yield GE_FUNCTION;
                else if (token.equals(LE)) yield LE_FUNCTION;
                else throw MatchException.GRAMMAR_ERROR.getThrow(null);
            }
            default -> throw MatchException.GRAMMAR_ERROR.getThrow(null);
        };
    }

    public static boolean check(String line) {
        return CompareTool.check(line);
    }

    @Override
    public Object replace() {
        double a = CastingToken.getDouble(A.replace());
        double b = CastingToken.getDouble(B.replace());
        return FUNCTION.apply(a, b);
    }

    @Override
    public String getReplace() {
        return KlassToken.BOOL_VARIABLE;
    }
}
