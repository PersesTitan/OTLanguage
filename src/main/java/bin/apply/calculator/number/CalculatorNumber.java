package bin.apply.calculator.number;

import bin.exception.VariableException;
import bin.parser.param.ParamToken;
import bin.token.CastingToken;
import bin.token.KlassToken;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class CalculatorNumber extends Calculator {
    private Object value = null;

    private static final Map<NumberItem, BiFunction<Object, Object, Object>> sing = Map.of(
            NumberItem.SUM, NumberTool::sum,
            NumberItem.SUB, NumberTool::sub,
            NumberItem.MUL, NumberTool::mul,
            NumberItem.DIV, NumberTool::div,
            NumberItem.REM, NumberTool::rem
    );

    public CalculatorNumber(String line) {
        super(line);
    }

    @Override
    public Object replace() {
        if (value == null) this.value = setValue();
        try {
            return value;
        } finally {
            value = null;
        }
    }

    @Override
    public String getReplace() {
        if (value == null) this.value = setValue();
        return getType();
    }

    private String getType() {
        if (value instanceof Integer) return KlassToken.INT_VARIABLE;
        else if (value instanceof Long) return KlassToken.LONG_VARIABLE;
        else if (value instanceof Float) return KlassToken.FLOAT_VARIABLE;
        else if (value instanceof Double) return KlassToken.DOUBLE_VARIABLE;
        else throw VariableException.VALUE_ERROR.getThrow(value);
    }

    private Object setValue() {
        List<Object> values = Arrays.stream(TOKENS)
                .map(ParamToken::replace)
                .map(CastingToken::getLong)
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
}
