package v4.calculator.bool;

import bin.exception.MatchException;
import v4.bin.apply.sys.item.TypeMap;
import v4.start.Return;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;

import static bin.exception.MatchException.GrammarTypeClass.OR_AND;
import static bin.exception.MatchException.GrammarTypeClass.TRUE_FALSE;
import static bin.token.cal.BoolToken.*;

public class BoolCalculator {
    public boolean start(Stack<String> stack, LinkedList<TypeMap> ra) {
        Collections.reverse(stack);
        boolean bool1 = getBoolean(stack, ra);
        while (!stack.isEmpty()) {
            bool1 = changeBoolean(bool1, stack.pop(), getBoolean(stack, ra));
        }
        return bool1;
    }

    private boolean changeBoolean(boolean bool1, String sing, boolean bool2) {
        return switch (sing) {
            case AND -> bool1 && bool2;
            case OR -> bool1 || bool2;
            default -> throw new MatchException().grammarTypeError(sing, OR_AND);
        };
    }

    private boolean getBoolean(Stack<String> stack, LinkedList<TypeMap> ra) {
        String line = stack.pop();
        if (line.equals(NOT)) {
            line = stack.pop();
            if (line.equals(TRUE)) return false;
            else if (line.equals(FALSE)) return true;
            else return !get(line, ra);
        } else {
            if (line.equals(TRUE)) return true;
            else if (line.equals(FALSE)) return false;
            else return get(line, ra);
        }
    }

    private boolean get(String line, LinkedList<TypeMap> ra) {
        String token = Return.replace(line, null, ra);
        if (token == null || !(token.equals(FALSE) || token.equals(TRUE)))
            throw new MatchException().grammarTypeError(token == null ? "" : token, TRUE_FALSE);
        else return token.equals(TRUE);
    }
}
