package etc.calculator.token;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import static bin.token.cal.BoolToken.FALSE;
import static bin.token.cal.BoolToken.TRUE;

public interface CalToken {
    Map<String, BiFunction<Double, Double, String>> compare = new HashMap<>() {{
        put(">", (a, b) -> a > b ? TRUE : FALSE);
        put("<", (a, b) -> a < b ? TRUE : FALSE);
        put("=", (a, b) -> a.equals(b) ? TRUE : FALSE);
        put(">=", (a, b) -> a >= b ? TRUE : FALSE);
        put("<=", (a, b) -> a <= b ? TRUE : FALSE);
    }};
}
