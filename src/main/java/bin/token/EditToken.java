package bin.token;

import bin.exception.MatchException;
import bin.token.check.CheckToken;

import java.util.Stack;
import java.util.StringTokenizer;

public interface EditToken {
    static String bothCut(String line, int s, int e) {
        return line.substring(s, line.length() - e);
    }

    static String bothCut(String line) {
        return line.substring(1, line.length() - 1);
    }

    static String toString(Object value) {
        return value instanceof Boolean b
                ? (b ? Token.TRUE : Token.FALSE)
                : value.toString();
    }

    static String[] split(String line, String token) {
        int i = line.indexOf(token);
        if (i == -1) return new String[] {line, ""};
        else return new String[] {
                line.substring(0, i),
                line.substring(i + token.length())
        };
    }

    static String[] split(String line, char c) {
        int i = line.indexOf(c);
        if (i == -1) return new String[] {line, ""};
        else return new String[] {
                line.substring(0, i),
                line.substring(i + 1)
        };
    }

    static String[] splitBlank(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line);
        String token = tokenizer.nextToken().stripTrailing();
        return new String[]{token, tokenizer.hasMoreTokens() ? tokenizer.nextToken("").stripLeading() : ""};
    }

    static int getAccess(String name) {
        if (!CheckToken.startWith(name, Token.ACCESS)) return 0;
        int count = 0;
        for (char c : name.toCharArray()) {
            if (c == Token.ACCESS) count++;
            else break;
        }
        return count;
    }

    static Stack<String> parser(String line) {
        Stack<String> stack = new Stack<>();
        int count = 0;
        StringBuilder builder = new StringBuilder();
        for (char c : line.toCharArray()) {
            switch (c) {
                case Token.PARAM_S -> {
                    count++;
                    builder.append(Token.PARAM_S);
                }
                case Token.PARAM_E -> {
                    if (count-- == 0) throw MatchException.GRAMMAR_ERROR.getThrow(line);
                    builder.append(Token.PARAM_E);
                    if (count == 0) {
                        stack.add(builder.toString());
                        builder.setLength(0);
                    }
                }
                default -> {
                    if (count == 0) {
                        if (Token.BLANKS.contains(c)) {
                            if (builder.length() != 0) {
                                stack.add(builder.toString());
                                builder.setLength(0);
                            }
                        } else builder.append(c);
                    } else builder.append(c);
                }
            }
        }

        if (count != 0) throw MatchException.GRAMMAR_ERROR.getThrow(line);
        if (builder.length() != 0) stack.add(builder.toString());
        return stack;
    }
}
