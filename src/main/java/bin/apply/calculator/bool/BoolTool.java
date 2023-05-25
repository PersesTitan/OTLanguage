package bin.apply.calculator.bool;

import bin.exception.MatchException;
import bin.token.Token;

import java.util.Stack;

interface BoolTool {
    static boolean check(String line) {
        int size = line.length();
        if (size<5 || (line.indexOf(Token.OR)<0 && line.indexOf(Token.AND)<0)) return false;
        Stack<Character> stack = new Stack<>();
        char[] cs = line.toCharArray();
        for (int i = 0; i<size; i++) {
            switch (cs[i]) {
                case Token.SET_S, Token.LIST_S, Token.MAP_S -> stack.add(cs[i]);
                case Token.SET_E -> {if (stack.pop() != Token.SET_S) throw MatchException.GRAMMAR_ERROR.getThrow(line);}
                case Token.LIST_E -> {if (stack.pop() != Token.LIST_S) throw MatchException.GRAMMAR_ERROR.getThrow(line);}
                case Token.MAP_E -> {if (stack.pop() != Token.MAP_S) throw MatchException.GRAMMAR_ERROR.getThrow(line);}
                case Token.OR, Token.AND -> {
                    if (stack.size() == 0 && 1 < i && i < size-1
                            && Token.BLANKS.contains(cs[i-1])
                            && Token.BLANKS.contains(cs[i+1])) return true;
                }
            }
        }
        return false;
    }

    default Stack<String> parser(String line) {
        StringBuilder builder = new StringBuilder();
        Stack<Character> count = new Stack<>();
        Stack<String> stack = new Stack<>();
        char[] cs = line.toCharArray();
        int size = line.length();
        try {
            for (int i = 0; i<size; i++) {
                switch (cs[i]) {
                    case Token.SET_S, Token.LIST_S, Token.MAP_S -> count.add(cs[i]);
                    case Token.SET_E -> {if (count.pop() != Token.SET_S) throw MatchException.GRAMMAR_ERROR.getThrow(line);}
                    case Token.LIST_E -> {if (count.pop() != Token.LIST_S) throw MatchException.GRAMMAR_ERROR.getThrow(line);}
                    case Token.MAP_E -> {if (count.pop() != Token.MAP_S) throw MatchException.GRAMMAR_ERROR.getThrow(line);}
                    case Token.OR, Token.AND -> {
                        if (count.size() == 0 && 1 < i && i < size-1
                                && Token.BLANKS.contains(cs[i-1])
                                && Token.BLANKS.contains(cs[i+1])) {
                            stack.add(builder.toString().strip());
                            builder.setLength(0);
                            stack.add(Character.toString(cs[i]));
                            i+=2;
                        }
                    }
                }
                builder.append(cs[i]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw MatchException.GRAMMAR_ERROR.getThrow(line);
        }

        if (builder.length() != 0) stack.add(builder.toString().strip());
        return stack;
    }
}
