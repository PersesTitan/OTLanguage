package bin.calculator.bool;

import bin.calculator.tool.CalculatorTool;
import bin.exception.MatchException;

import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

import static bin.exception.MatchException.GrammarTypeClass.*;

public class CompareCalculator implements CalculatorTool {
    public Stack<String> start(Stack<String> stack, LinkedList<Map<String, Map<String, Object>>> ra) {
        compare.keySet().forEach(v -> search(stack, v, ra));
        return stack;
    }

    private void search(Stack<String> stack, String token, LinkedList<Map<String, Map<String, Object>>> ra) {
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
