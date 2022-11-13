package bin.calculator.tool;

import bin.CreateReturnWorks;
import bin.exception.MatchException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.BiFunction;

import static bin.exception.MatchException.*;
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

    default double getDouble(String sing, LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        try {return Double.parseDouble(sing);}
        catch (Exception e) {
            String str = CreateReturnWorks.sub(sing, null, repositoryArray);
            try {return Double.parseDouble(str);}
            catch (Exception e1) {throw new MatchException().grammarTypeError(str, GrammarTypeClass.NUMBER);}
        }
    }
}
