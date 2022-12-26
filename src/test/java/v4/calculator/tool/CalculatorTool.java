package v4.calculator.tool;

import bin.exception.MatchException;
import v4.bin.apply.sys.item.TypeMap;
import v4.start.Return;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.BiFunction;

import static bin.exception.MatchException.GrammarTypeClass;
import static bin.token.cal.BoolToken.FALSE;
import static bin.token.cal.BoolToken.TRUE;

public interface CalculatorTool {
    Map<String, BiFunction<Double, Double, String>> compare = new HashMap<>() {{
        put(">", (a, b) -> a > b ? TRUE : FALSE);
        put("<", (a, b) -> a < b ? TRUE : FALSE);
        put("<=", (a, b) -> a <= b ? TRUE : FALSE);
        put(">=", (a, b) -> a >= b ? TRUE : FALSE);
        put("=", (a, b) -> a.equals(b) ? TRUE : FALSE);
    }};

    Map<String, BiFunction<Double, Double, Double>> number = new HashMap<>() {{
        put("+", Double::sum);
        put("-", (a, b) -> a - b);
        put("*", (a, b) -> a * b);
        put("/", (a, b) -> a / b);
        put("%", (a, b) -> a % b);
    }};

    default double getDouble(String sing, LinkedList<TypeMap> repositoryArray) {
        try {return Double.parseDouble(sing);}
        catch (Exception e) {
            String str = Return.replace(sing, null, repositoryArray);
            try {return Double.parseDouble(str);}
            catch (Exception e1) {throw new MatchException().grammarTypeError(str, GrammarTypeClass.NUMBER);}
        }
    }
}
