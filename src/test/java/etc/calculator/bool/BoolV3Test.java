package etc.calculator.bool;

import bin.CreateReturnWorks;
import bin.exception.MatchException;
import etc.calculator.token.CalToken;

import java.util.*;
import java.util.function.BiFunction;

import static bin.token.Token.NO_TOKEN;
import static bin.token.Token.TOKEN;
import static bin.token.cal.BoolToken.*;

public class BoolV3Test implements CalToken {
    public static void main(String[] args) {

    }

    private final Stack<String> stack = new Stack<>();
    public Stack<String> start(String line, LinkedList<Map<String, Map<String, Object>>> ra) {
        stack.clear();
        StringTokenizer tokenizer = new StringTokenizer(line, "ㅇㄸㄲㄴ", true);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (token.isBlank()) continue;
            if (token.equals(TOKEN)) {
                String a = tokenizer.nextToken();
                if (a.equals(TOKEN)) stack.add(TRUE);
                else if (a.equals(NO_TOKEN)) stack.add(NOT);
                else {
                    if (tokenizer.nextToken().equals(TOKEN)) stack.add(a);
                    else throw new MatchException().grammarTypeError();
                }
            } else if (token.equals(NO_TOKEN)) {
                if (tokenizer.nextToken().equals(NO_TOKEN)) stack.add(FALSE);
                else throw new MatchException().grammarTypeError();
            } else stack.add(token.strip());
        }

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
