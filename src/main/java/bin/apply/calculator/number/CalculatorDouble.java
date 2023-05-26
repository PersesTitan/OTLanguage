package bin.apply.calculator.number;

import bin.parser.param.ParamToken;
import bin.token.CastingToken;
import bin.token.KlassToken;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class CalculatorDouble extends Calculator {
    private static final Map<NumberItem, BiFunction<Double, Double, Double>> sing = Map.of(
            NumberItem.SUM, Double::sum,
            NumberItem.SUB, (a, b) -> a - b,
            NumberItem.MUL, (a, b) -> a * b,
            NumberItem.DIV, (a, b) -> a / b,
            NumberItem.REM, (a, b) -> a % b
    );

    public CalculatorDouble(String line) {
        super(line);
    }

    @Override
    public Object replace() {
        List<Double> values = Arrays.stream(TOKENS)
                .map(ParamToken::replace)
                .map(CastingToken::getDouble)
                .collect(Collectors.toList());
        int count = 0;
        for (int order : ORDER_S) {
            NumberItem ITEM = SINGS[order];
            order -= count++;
            values.set(order, sing.get(ITEM).apply(values.get(order), values.remove(order+1)));
        }
        for (int order : ORDER_E) values.set(0, sing.get(SINGS[order]).apply(values.get(0), values.remove(1)));
        return values.get(0);
    }

    @Override
    public String getReplace() {
        return KlassToken.DOUBLE_VARIABLE;
    }
}
