package etc.v3.calculator.bool;

import etc.v3.calculator.token.CalToken;

import java.util.*;

public class CompareV3Test implements CalToken {
    public Stack<String> start(Stack<String> stack, LinkedList<Map<String, Map<String, Object>>> ra) {
        compare.keySet().forEach(v -> search(stack, v, ra));
        return stack;
    }

    private void search(Stack<String> stack, String token, LinkedList<Map<String, Map<String, Object>>> ra) {
        int i;
        while ((i = stack.indexOf(token)) != -1) {
            double d1 = getDouble(stack.get(i-1), ra);
            double d2 = getDouble(stack.get(i+1), ra);
            stack.set(i ,  compare.get(stack.get(i)).apply(d1, d2));
            stack.remove(i+1);
            stack.remove(i-1);
        }
    }
}
