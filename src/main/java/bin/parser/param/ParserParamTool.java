package bin.parser.param;

import bin.apply.calculator.bool.CalculatorBool;
import bin.apply.calculator.bool.CalculatorCompare;
import bin.apply.calculator.bool.CalculatorNot;
import bin.apply.calculator.number.*;
import bin.apply.mode.TypeMode;
import bin.exception.MatchException;
import bin.parser.param.variable.ParamListItem;
import bin.parser.param.variable.ParamMapItem;
import bin.parser.param.variable.ParamOriginItem;
import bin.parser.param.variable.ParamSetItem;
import bin.token.check.CheckToken;
import bin.token.EditToken;
import bin.token.Token;

import java.util.Stack;
import java.util.StringTokenizer;

import static work.ResetWork.*;
import static work.ResetWork.c;

public interface ParserParamTool {
    String ACCESS = Character.toString(Token.ACCESS);

    default Stack<String> parser(int access, String line) {
        char[] cs = line.substring(access).toCharArray();
        int count = 0;
        Stack<String> stack = new Stack<>();
        StringBuilder builder = new StringBuilder();
        for (char c : cs) {
            switch (c) {
                case Token.PARAM_S -> {
                    count++;
                    builder.append(Token.PARAM_S);
                }
                case Token.PARAM_E -> {
                    if (count-- == 0) throw MatchException.GRAMMAR_ERROR.getThrow(line);
                    builder.append(Token.PARAM_E);
                }
                case Token.ACCESS -> {
                    if (count == 0) {
                        stack.add(builder.toString());
                        builder.setLength(0);
                    } else builder.append(Token.ACCESS);
                }
                default -> builder.append(c);
            }
        }

        if (count != 0) throw MatchException.GRAMMAR_ERROR.getThrow(line);
        if (!builder.isEmpty()) stack.add(builder.toString());
        return stack;
    }

    static ParamToken start(String type, String value) {
        if (CheckToken.startWith(value, Token.REPLACE_S)) return new ParserParamItem(value).start();
        try {
            return valueOf(type, value);
        } catch (NumberFormatException e) {
            return new ParserParamItem(value).start();
        }
    }

    static ParamToken start(TypeMode TYPE, String value) {
        return start(TYPE.getType(), value);
    }

    // CREATE
    private static ParamToken valueOf(String type, String value) {
        return switch (type) {
            case i -> NumberToken.check(value)
                    ? new CalculatorInteger(value)
                    : new ParamOriginItem(type, Integer.parseInt(value));
            case l -> NumberToken.check(value)
                    ? new CalculatorLong(value)
                    : new ParamOriginItem(type, Long.parseLong(value));
            case f -> NumberToken.check(value)
                    ? new CalculatorFloat(value)
                    : new ParamOriginItem(type, Float.parseFloat(value));
            case d -> NumberToken.check(value)
                    ? new CalculatorDouble(value)
                    : new ParamOriginItem(type, Double.parseDouble(value));
            case s -> new ParamOriginItem(type, value);
            case b -> {
                if (CalculatorBool.check(value)) yield new CalculatorBool(value);
                else if (CalculatorNot.check(value)) yield new CalculatorNot(value);
                else if (CalculatorCompare.check(value)) yield new CalculatorCompare(value);
                else yield new ParamOriginItem(type, switch (value) {
                    case Token.TRUE -> true;
                    case Token.FALSE -> false;
                    default -> throw new NumberFormatException();
                });
            }
            case c -> {
                if (value.length() == 1) yield new ParamOriginItem(type, value.charAt(0));
                else throw new NumberFormatException();
            }

            case si -> getSet(type, Integer.class, TypeMode.INTEGER, i, value);
            case sl -> getSet(type, Long.class, TypeMode.LONG, l, value);
            case sb -> getSet(type, Boolean.class, TypeMode.BOOLEAN, b, value);
            case ss -> getSet(type, String.class, TypeMode.STRING, s, value);
            case sc -> getSet(type, Character.class, TypeMode.CHARACTER, c, value);
            case sf -> getSet(type, Float.class, TypeMode.FLOAT, f, value);
            case sd -> getSet(type, Double.class, TypeMode.DOUBLE, d, value);

            case li -> getList(type, Integer.class, TypeMode.INTEGER, i, value);
            case ll -> getList(type, Long.class, TypeMode.LONG, l, value);
            case lb -> getList(type, Boolean.class, TypeMode.BOOLEAN, b, value);
            case ls -> getList(type, String.class, TypeMode.STRING, s, value);
            case lc -> getList(type, Character.class, TypeMode.CHARACTER, c, value);
            case lf -> getList(type, Float.class, TypeMode.FLOAT, f, value);
            case ld -> getList(type, Double.class, TypeMode.DOUBLE, d, value);

            case mi -> getMap(type, Integer.class, TypeMode.INTEGER, i, value);
            case ml -> getMap(type, Long.class, TypeMode.LONG, l, value);
            case mb -> getMap(type, Boolean.class, TypeMode.BOOLEAN, b, value);
            case ms -> getMap(type, String.class, TypeMode.STRING, s, value);
            case mc -> getMap(type, Character.class, TypeMode.CHARACTER, c, value);
            case mf -> getMap(type, Float.class, TypeMode.FLOAT, f, value);
            case md -> getMap(type, Double.class, TypeMode.DOUBLE, d, value);

            default -> throw new NumberFormatException();
        };
    }

    private static <T> ParamToken getSet(String main, Class<T> klass, TypeMode MODE, String type, String value) {
        if (CheckToken.isSet(value)) {
            StringTokenizer tokenizer = new StringTokenizer(EditToken.bothCut(value), Token.COMMA);
            int count = tokenizer.countTokens();
            ParamToken[] params = new ParamToken[count];
            for (int i = 0; i < count; i++) params[i] = valueOf(type, tokenizer.nextToken().strip());
            return new ParamSetItem<T>(MODE, params, main);
        } else throw new NumberFormatException();
    }

    private static <T> ParamToken getList(String main, Class<T> klass, TypeMode MODE, String type, String value) {
        if (CheckToken.isList(value)) {
            StringTokenizer tokenizer = new StringTokenizer(EditToken.bothCut(value), Token.COMMA);
            int count = tokenizer.countTokens();
            ParamToken[] params = new ParamToken[count];
            for (int i = 0; i < count; i++) params[i] = valueOf(type, tokenizer.nextToken().strip());
            return new ParamListItem<T>(MODE, params, main);
        } else throw new NumberFormatException();
    }

    private static <V> ParamToken getMap(String main, Class<V> klass, TypeMode MODE, String type, String value) {
        if (CheckToken.isMap(value)) {
            StringTokenizer tokenizer = new StringTokenizer(EditToken.bothCut(value), Token.COMMA);
            int size = tokenizer.countTokens();
            ParamToken[] keys = new ParamToken[size];
            ParamToken[] values = new ParamToken[size];
            for (int p, i = 0; i < size; i++) {
                String token = tokenizer.nextToken();
                if ((p = token.indexOf('='))>=0) {
                    keys[i] = valueOf(s, token.substring(0, p).strip());
                    values[i] = valueOf(type, token.substring(i + 1).strip());
                } else throw MatchException.GRAMMAR_ERROR.getThrow(value);
            }
            return new ParamMapItem<String, V>(TypeMode.STRING, MODE, keys, values, main);
        } else throw new NumberFormatException();
    }
}
