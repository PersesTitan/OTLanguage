package v4.calculator.bool;

import bin.exception.MatchException;
import v4.bin.apply.sys.item.TypeMap;
import v4.calculator.tool.CalculatorTool;

import java.util.LinkedList;
import java.util.Stack;

import static bin.exception.MatchException.GrammarTypeClass.COMPARE;

public class CompareCalculator implements CalculatorTool {
    public Stack<String> start(Stack<String> stack, LinkedList<TypeMap> ra) {
        compare.keySet().forEach(v -> search(stack, v, ra));
        return stack;
    }

    private void search(Stack<String> stack, String token, LinkedList<TypeMap> ra) {
        int i;
        while ((i = stack.indexOf(token)) != -1) {
            double d1 = getDouble(stack.get(i-1), ra);
            double d2 = getDouble(stack.get(i+1), ra);
            if (!compare.containsKey(stack.get(i))) throw new MatchException().grammarTypeError(stack.get(i), COMPARE);
            stack.set(i, compare.get(stack.get(i)).apply(d1, d2));
            stack.remove(i+1);
            stack.remove(i-1);
        }
    }
}
