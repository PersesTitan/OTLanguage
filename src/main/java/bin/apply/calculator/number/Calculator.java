package bin.apply.calculator.number;

import bin.exception.MatchException;
import bin.parser.param.ParamToken;
import bin.parser.param.ParserParamItem;

import java.util.Collections;
import java.util.Stack;

abstract class Calculator extends ParamToken implements NumberToken {
    final ParamToken[] TOKENS;
    final NumberItem[] SINGS;
    final int[] ORDER_S, ORDER_E;

    public Calculator(String line) {
        Stack<String> stack = parser(line);
        Collections.reverse(stack);

        int size = stack.size();
        if (size % 2 == 0) throw MatchException.NUMBER_GRAMMAR_ERROR.getThrow(line);
        Stack<NumberItem> SINGS = new Stack<>();
        Stack<ParamToken> TOKENS = new Stack<>();
        for (int i = 0; i<size; i++) {
            if (i % 2 == 0) TOKENS.add(new ParserParamItem(stack.pop()).start());
            else SINGS.add(NumberItem.get(stack.pop(), line));
        }
        this.TOKENS = TOKENS.toArray(ParamToken[]::new);
        this.SINGS = SINGS.toArray(NumberItem[]::new);
        if (SINGS.size() + 1 != TOKENS.size()) throw MatchException.NUMBER_GRAMMAR_ERROR.getThrow(line);
        Stack<Integer> SINGS_S = new Stack<>();
        Stack<Integer> SINGS_E = new Stack<>();
        size = SINGS.size();
        for (int i = 0; i<size; i++) {
            if (SINGS.get(i).is()) SINGS_S.add(i);
            else SINGS_E.add(i);
        }
        this.ORDER_S = SINGS_S.stream().mapToInt(v -> v).toArray();
        this.ORDER_E = SINGS_E.stream().mapToInt(v -> v).toArray();
    }
}
