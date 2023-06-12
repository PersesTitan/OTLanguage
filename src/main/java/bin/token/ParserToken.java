package bin.token;

import bin.exception.MatchException;
import bin.token.check.CheckToken;

import java.util.*;

public interface ParserToken {
    static Stack<String> parser(String line, boolean eParam) {
        Stack<String> stack = new Stack<>();
        int count = 0;
        StringBuilder builder = new StringBuilder();
        for (char c : line.toCharArray()) {
            switch (c) {
                case Token.PARAM_S -> {
                    if (count++ == 0) {
                        if (eParam) builder.append(Token.PARAM_S);
                    } else builder.append(Token.PARAM_S);
                }
                case Token.PARAM_E -> {
                    if (count-- == 0) throw MatchException.GRAMMAR_ERROR.getThrow(line);
                    if (count == 0) {
                        if (eParam) builder.append(Token.PARAM_E);
                        stack.add(builder.toString());
                        builder.setLength(0);
                    } else builder.append(Token.PARAM_E);
                }
                default -> {
                    if (count != 0) builder.append(c);
                    else if (Token.BLANKS.contains(c)) {
                        if (builder.length() != 0) {
                            stack.add(builder.toString());
                            builder.setLength(0);
                        }
                    } else builder.append(c);
                }
            }
        }

        if (count != 0) throw MatchException.GRAMMAR_ERROR.getThrow(line);
        if (builder.length() != 0) stack.add(builder.toString());
        return stack;
    }

    static Stack<String> parser(String line) {
        return parser(line, true);
    }

    static Stack<String> blankParser(String line) {
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
                }
                default -> {
                    if (count == 0 && Token.BLANKS.contains(c)) {
                        if (builder.length() != 0) {
                            stack.add(builder.toString());
                            builder.setLength(0);
                        }
                    } else builder.append(c);
                }
            }
        }

        if (count != 0) throw MatchException.GRAMMAR_ERROR.getThrow(line);
        if (builder.length() != 0) stack.add(builder.toString());
        return stack;
    }

    static String[] paramParser(String line) {
        char[] cs = line.toCharArray();
        int position = 0;
        for (char c : cs) {
            if (c == Token.PARAM_S || Token.BLANKS.contains(c)) break;
            else position++;
        }
        return new String[] {
                line.substring(0, position),
                line.substring(position)
        };
    }

    static String[] param(String param) {
        return param(param, false);
    }

    static String[] param(String param, boolean isList) {
        if (param.isEmpty()) return new String[0];
        if (isList) return new String[] {param};
        else if (CheckToken.isParams(param)) return centerSplit(param);
        else if (CheckToken.isBlank(param.charAt(0))) return new String[]{param.stripLeading()};
        else return new String[] {param};
    }

    private static String[] centerSplit(String line) {
        Stack<Character> stack = new Stack<>();
        Stack<Integer> list = new Stack<>();
        int size = line.length() - 1;
        char[] cs = line.toCharArray();
        for (int i = 1; i<size; i++) {
            switch (cs[i]) {
                case Token.SET_S, Token.MAP_S, Token.PARAM_S -> stack.add(cs[i]);
                case Token.SET_E -> {if (stack.pop() != Token.SET_S) throw MatchException.GRAMMAR_ERROR.getThrow(line);}
                case Token.MAP_E -> {if (stack.pop() != Token.MAP_S) throw MatchException.GRAMMAR_ERROR.getThrow(line);}
                case Token.PARAM_E -> {
                    if (stack.size() == 0 && cs[i+1] == Token.PARAM_S) list.add(i++);
                    else if (stack.pop() != Token.PARAM_S) throw MatchException.GRAMMAR_ERROR.getThrow(line);
                }
            }
        }

        String[] params = switch (list.size()) {
            case 0 -> new String[] {line};
            case 1 -> {
                int i = list.pop() + 1;
                yield new String[] {line.substring(0, i), line.substring(i)};
            }
            default -> {
                Collections.reverse(list);
                String[] values = new String[list.size() + 1];
                int p = 0, i = 0;
                while (!list.isEmpty()) {
                    if (i == 0) values[0] = line.substring(0, (p = list.pop() + 1));
                    else values[i] = line.substring(p, (p = list.pop() + 1));
                    i++;
                }
                values[i] = line.substring(p);
                yield values;
            }
        };

        size = params.length;
        for (int i = 0; i<size; i++) params[i] = EditToken.bothCut(params[i]);
        return params;
    }

    static Stack<String> parser(String line, String token) {
        int size = line.length(), tokenSize = token.length();
        Stack<String> s = new Stack<>();
        if (size <= tokenSize) {
            s.add(line);
            return s;
        }
        Stack<Character> stack = new Stack<>();
        List<Integer> list = new ArrayList<>();
        char[] cs = line.toCharArray();
        char[] ts = token.toCharArray();

        for (int i = 0; i<size; i++) {
            switch (cs[i]) {
                case Token.SET_S, Token.MAP_S, Token.PARAM_S -> stack.add(cs[i]);
                case Token.SET_E -> {if (stack.pop() != Token.SET_S) throw MatchException.GRAMMAR_ERROR.getThrow(line);}
                case Token.MAP_E -> {if (stack.pop() != Token.MAP_S) throw MatchException.GRAMMAR_ERROR.getThrow(line);}
                default -> {
                    if (cs[i] == ts[0] && stack.size() == 0) {
                        boolean b = true;
                        try {
                            for (int j = 0; j < tokenSize; j++) {
                                if (ts[j] != cs[i+j]) {
                                    b = false;
                                    break;
                                }
                            }
                        } catch (ArrayIndexOutOfBoundsException ignored) {
                            b = false;
                        }
                        if(b) list.add(i);
                    }
                }
            }
        }
        int start = 0;
        for (int i : list) {
            if (start == 0) s.add(line.substring(start, (start = i)));
            else s.add(line.substring(start + tokenSize, (start = i)));
        }

        if (list.size() == 0) s.add(line);
        else s.add(line.substring(start + tokenSize));
        return s;
    }
}
