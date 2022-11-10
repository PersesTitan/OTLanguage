package etc.calculator.bool;

import bin.exception.MatchException;

import java.util.Collections;
import java.util.Stack;

import static bin.token.cal.BoolToken.*;

public class BoolV3Test {
    public boolean start(Stack<String> stack) {
        Collections.reverse(stack);
        boolean bool1 = getBoolean(stack);
        while (!stack.isEmpty()) {
            bool1 = changeBoolean(
                    bool1,
                    stack.pop(),
                    getBoolean(stack));
        }
        return bool1;
    }

    private boolean changeBoolean(boolean bool1, String sing, boolean bool2) {
        return switch (sing) {
            case AND -> bool1 && bool2;
            case OR -> bool1 || bool2;
            default -> throw new MatchException().grammarTypeError();
        };
    }

    private boolean getBoolean(Stack<String> stack) {
        String line = stack.pop();
        if (line.equals(NOT)) {
            line = stack.pop();
            if (line.equals(TRUE)) return false;
            else if (line.equals(FALSE)) return true;
            else throw new MatchException().grammarTypeError();
        } else {
            if (line.equals(TRUE)) return true;
            else if (line.equals(FALSE)) return false;
            else throw new MatchException().grammarTypeError();
        }
    }

}
