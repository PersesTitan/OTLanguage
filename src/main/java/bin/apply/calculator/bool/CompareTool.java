package bin.apply.calculator.bool;

import bin.exception.MatchException;
import bin.token.Token;

import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.function.BiFunction;

interface CompareTool {
    char GT = '>', LT = '<', EQ = '=', NQ = '!';
    String GE = ">=", LE = "<=";
    Set<Character> sing = Set.of(GT, LT, EQ, NQ);
    BiFunction<Double, Double, Boolean>
            GT_FUNCTION = (a, b) -> a > b,  LT_FUNCTION = (a, b) -> a < b,
            GE_FUNCTION = (a, b) -> a >= b, LE_FUNCTION = (a, b) -> a <= b,
            EQ_FUNCTION = Objects::equals,  NQ_FUNCTION = (a, b) -> !Objects.equals(a, b);

    static boolean check(String line) {
        int size = line.length();
        if (size < 7) return false;
        Stack<Character> stack = new Stack<>();
        char[] cs = line.toCharArray();
        for (int i = 0; i<size; i++) {
            switch (cs[i]) {
                case Token.SET_S, Token.LIST_S, Token.MAP_S -> stack.add(cs[i]);
                case Token.SET_E -> {if (stack.pop() != Token.SET_S) throw MatchException.GRAMMAR_ERROR.getThrow(line);}
                case Token.LIST_E -> {if (stack.pop() != Token.LIST_S) throw MatchException.GRAMMAR_ERROR.getThrow(line);}
                case Token.MAP_E -> {if (stack.pop() != Token.MAP_S) throw MatchException.GRAMMAR_ERROR.getThrow(line);}
                case Token.TOKEN -> {
                    if (stack.size() == 0 && 1 < i && i < size-4 && Token.BLANKS.contains(cs[i-1])) {
                        if (cs[i+2] == Token.TOKEN && Token.BLANKS.contains(cs[i+3]) && sing.contains(cs[i+1])) return true;
                        else if (cs[i+3] == Token.TOKEN && cs[i+2] == EQ
                                && Token.BLANKS.contains(cs[i+4]) && (cs[i+1] == GT || cs[i+1] == LT)) return true;
                    }
                }
            }
        }
        return false;
    }

    default int compare(String line) {
        Stack<Character> count = new Stack<>();
        char[] cs = line.toCharArray();
        int size = line.length();
        for (int i = 0; i<size; i++) {
            switch (cs[i]) {
                case Token.SET_S, Token.LIST_S, Token.MAP_S -> count.add(cs[i]);
                case Token.SET_E -> {if (count.pop() != Token.SET_S) throw MatchException.GRAMMAR_ERROR.getThrow(line);}
                case Token.LIST_E -> {if (count.pop() != Token.LIST_S) throw MatchException.GRAMMAR_ERROR.getThrow(line);}
                case Token.MAP_E -> {if (count.pop() != Token.MAP_S) throw MatchException.GRAMMAR_ERROR.getThrow(line);}
                case Token.TOKEN -> {
                    if (count.size() == 0 && 1 < i && i < size-4 && Token.BLANKS.contains(cs[i-1])) {
                        if (cs[i+2] == Token.TOKEN && Token.BLANKS.contains(cs[i+3]) && sing.contains(cs[i+1])) return i;
                        else if (cs[i+3] == Token.TOKEN && Token.BLANKS.contains(cs[i+4])) {
                            if (cs[i+2] == EQ && (cs[i+1] == GT || cs[i+1] == LT)) return i;
                        }
                    }
                }
            }
        }
        throw MatchException.GRAMMAR_ERROR.getThrow(line);
    }
}
