package bin.apply.calculator.bool;

import bin.exception.MatchException;
import bin.parser.param.ParamToken;
import bin.parser.param.ParserParamItem;
import bin.token.CastingToken;
import bin.token.KlassToken;

import java.util.Collections;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiFunction;

public class CalculatorBool extends ParamToken implements BoolTool {
    private final ParamToken[] TOKENS;
    private final BoolItem[] ITEMS;
    private static final Map<BoolItem, BiFunction<Boolean, Boolean, Boolean>> sing = Map.of(
            BoolItem.OR, (a, b) -> a || b,
            BoolItem.AND, (a, b) -> a && b
    );

    public static boolean check(String line) {
        return BoolTool.check(line);
    }

    public CalculatorBool(String line) {
        Stack<String> stack = parser(line);
        Collections.reverse(stack);

        int size = stack.size();
        if (size % 2 == 0) throw MatchException.NUMBER_GRAMMAR_ERROR.getThrow(line);
        Stack<ParamToken> TOKENS = new Stack<>();
        Stack<BoolItem> ITEMS = new Stack<>();
        for (int i = 0; i<size; i++) {
            if (i % 2 == 0) TOKENS.add(new ParserParamItem(stack.pop()).start());
            else ITEMS.add(BoolItem.get(stack.pop(), line));
        }

        if (ITEMS.size() + 1 != TOKENS.size()) throw MatchException.NUMBER_GRAMMAR_ERROR.getThrow(line);
        this.TOKENS = TOKENS.toArray(ParamToken[]::new);
        this.ITEMS = ITEMS.toArray(BoolItem[]::new);
    }

    @Override
    public Object replace() {
        int size = TOKENS.length;
        boolean value = CastingToken.getBoolean(TOKENS[0].replace());
        for (int i = 1; i<size; i++) value = sing.get(ITEMS[i-1]).apply(value, CastingToken.getBoolean(TOKENS[i].replace()));
        return value;
    }

    @Override
    public String getReplace() {
        return KlassToken.BOOL_VARIABLE;
    }
}
