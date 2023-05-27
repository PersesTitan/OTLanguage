package bin.apply.calculator.number;

import bin.parser.param.ParamToken;
import bin.token.CastingToken;
import bin.token.KlassToken;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class CalculatorInteger extends Calculator {
    private static final Map<NumberItem, BiFunction<Integer, Integer, Integer>> sing = Map.of(
            NumberItem.SUM, Integer::sum,
            NumberItem.SUB, (a, b) -> a - b,
            NumberItem.MUL, (a, b) -> a * b,
            NumberItem.DIV, (a, b) -> a / b,
            NumberItem.REM, (a, b) -> a % b
    );

    public CalculatorInteger(String line) {
        super(line);
    }

    @Override
    public Object replace() {
        List<Integer> values = Arrays.stream(TOKENS)
                .map(ParamToken::replace)
                .map(CastingToken::getInt)
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
        return KlassToken.INT_VARIABLE;
    }
}
