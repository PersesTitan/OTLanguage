package bin.parser.param.sub;

import bin.exception.VariableException;
import bin.parser.param.ParamToken;
import bin.parser.param.ParserParamItem;
import bin.token.CastingToken;
import bin.token.KlassToken;
import bin.token.check.CheckToken;
import bin.token.EditToken;
import bin.token.Token;
import bin.variable.OtherList;
import bin.variable.OtherMap;
import bin.variable.OtherSet;
import bin.variable.custom.CustomList;
import bin.variable.custom.CustomMap;
import bin.variable.custom.CustomSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static work.ResetWork.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ParamSubItem extends ParamToken {
    private final ParamToken PARAMS, SUB_TOKEN;
    private final SubItem SUB;

    private static ParamToken noSub(ParamToken PARAMS, String line, int e) {
        return PARAMS == null
                ? new ParserParamItem(EditToken.bothCut(line, 0, e)).start()
                : ParserParamItem.other(PARAMS, EditToken.bothCut(line, 0, e));
    }

    private static ParamToken sub(ParamToken PARAMS, String tokens) {
        return PARAMS == null
                ? new ParserParamItem(tokens).start()
                : ParserParamItem.other(PARAMS, tokens);
    }

    public static ParamSubItem create(ParamToken PARAMS, String line) {
        if (line.length() == 1) return null;
        if (CheckToken.endWith(line, Token.CLEAR)) {
            return new ParamSubItem(noSub(PARAMS, line, 1), null, SubItem.CLEAR);
        } else if (CheckToken.endWith(line, Token.IS_EMPTY)) {
            return new ParamSubItem(noSub(PARAMS, line, 1), null, SubItem.IS_EMPTY);
        } else if (CheckToken.endWith(line, Token.MAX)) {
            return new ParamSubItem(noSub(PARAMS, line, 1), null, SubItem.MAX);
        } else if (CheckToken.endWith(line, Token.MIN)) {
            return new ParamSubItem(noSub(PARAMS, line, 1), null, SubItem.MIN);
        } else if (line.endsWith(Token.SUM)) {
            if (line.startsWith(Token.SUM)) return null;
            return new ParamSubItem(noSub(PARAMS, line, 2), null, SubItem.SUM);
        } else if (line.contains(Token.CONTAINS_V)) {
            if (line.startsWith(Token.CONTAINS_V)) return null;
            String[] tokens = EditToken.split(line, Token.CONTAINS_V);
            ParamToken token = new ParserParamItem(tokens[1]).start();
            return new ParamSubItem(sub(PARAMS, tokens[0]), token, SubItem.CONTAINS_V);
        } else if (line.contains(Token.CONTAINS)) {
            if (line.startsWith(Token.CONTAINS)) return null;
            String[] tokens = EditToken.split(line, Token.CONTAINS);
            ParamToken token = new ParserParamItem(tokens[1]).start();
            return new ParamSubItem(sub(PARAMS, tokens[0]), token, SubItem.CONTAINS);
        } else if (line.contains(Token.ADD)) {
            if (line.startsWith(Token.ADD)) return null;
            String[] tokens = EditToken.split(line, Token.ADD);
            ParamToken token = new ParserParamItem(tokens[1]).start();
            return new ParamSubItem(sub(PARAMS, tokens[0]), token, SubItem.ADD);
        } else if (line.contains(Token.GET)) {
            if (line.startsWith(Token.GET)) return null;
            String[] tokens = EditToken.split(line, Token.GET);
            if (!CheckToken.isUseName(tokens[0])) return null;
            ParamToken token = new ParserParamItem(tokens[1]).start();
            return new ParamSubItem(sub(PARAMS, tokens[0]), token, SubItem.GET);
        } return null;
    }

    @Override
    public Object replace() {
        Object value = PARAMS.replace();
        return switch (SUB) {
            case MAX -> {
                if (value instanceof CustomSet<?> set) yield set.max();
                else if (value instanceof CustomList<?> list) yield list.max();
                else if (value instanceof CustomMap<?,?> map) yield map.max();
                else throw VariableException.TYPE_ERROR.getThrow(SUB_TOKEN.replace());
            }
            case MIN -> {
                if (value instanceof CustomSet<?> set) yield set.min();
                else if (value instanceof CustomList<?> list) yield list.min();
                else if (value instanceof CustomMap<?,?> map) yield map.min();
                else throw VariableException.TYPE_ERROR.getThrow(SUB_TOKEN.replace());
            }
            case SUM -> {
                if (value instanceof CustomSet<?> set) yield set.sum();
                else if (value instanceof CustomList<?> list) yield list.sum();
                else if (value instanceof CustomMap<?,?> map) yield map.sum();
                else throw VariableException.TYPE_ERROR.getThrow(SUB_TOKEN.replace());
            }
            case CLEAR -> {
                if (value instanceof CustomSet<?> set) set.clear();
                else if (value instanceof CustomList<?> list) list.clear();
                else if (value instanceof CustomMap<?,?> map) map.clear();
                else if (value instanceof OtherSet<?> set) set.clear();
                else if (value instanceof OtherList<?> list) list.clear();
                else if (value instanceof OtherMap<?,?> map) map.clear();
                else throw VariableException.TYPE_ERROR.getThrow(SUB_TOKEN.replace());
                yield null;
            }
            case IS_EMPTY -> {
                if (value instanceof String str) yield str.isEmpty();
                else if (value instanceof CustomSet<?> set) yield set.isEmpty();
                else if (value instanceof CustomList<?> list) yield list.isEmpty();
                else if (value instanceof CustomMap<?,?> map) yield map.isEmpty();
                else if (value instanceof OtherSet<?> set) yield set.isEmpty();
                else if (value instanceof OtherList<?> list) yield list.isEmpty();
                else if (value instanceof OtherMap<?,?> map) yield map.isEmpty();
                else throw VariableException.TYPE_ERROR.getThrow(SUB_TOKEN.replace());
            }
            case CONTAINS -> {
                Object sub = SUB_TOKEN.replace();
                if (value instanceof String str) yield str.contains(sub.toString());
                else if (value instanceof CustomSet<?> set) yield set.contains(sub);
                else if (value instanceof CustomList<?> list) yield list.contains(sub);
                else if (value instanceof CustomMap<?,?> map) yield map.containsKey(sub);
                else if (value instanceof OtherSet<?> set) yield set.contains(sub);
                else if (value instanceof OtherList<?> list) yield list.contains(sub);
                else if (value instanceof OtherMap<?,?> map) yield map.containsKey(sub);
                else throw VariableException.TYPE_ERROR.getThrow(sub);
            }
            case CONTAINS_V -> {
                Object sub = SUB_TOKEN.replace();
                if (value instanceof CustomMap<?,?> map) yield map.containsValue(sub);
                else if (value instanceof OtherMap<?,?> map) yield map.containsValue(sub);
                else throw VariableException.TYPE_ERROR.getThrow(sub);
            }
            case GET -> {
                Object sub = SUB_TOKEN.replace();
                if (value instanceof CustomList<?> list) yield list.get(CastingToken.getInt(sub));
                else if (value instanceof OtherList<?> list) yield list.get(CastingToken.getInt(sub));
                else if (value instanceof CustomSet<?> set) yield set.get(CastingToken.getInt(sub));
                else if (value instanceof OtherSet<?> set) yield set.get(CastingToken.getInt(sub));
                else if (value instanceof String str) yield str.charAt(CastingToken.getInt(sub));
                else if (value instanceof CustomMap<?,?> map) yield map.get(sub);
                else if (value instanceof OtherMap<?,?> map) yield map.get(sub);
                else throw VariableException.TYPE_ERROR.getThrow(sub);
            }
            case ADD -> {
                Object sub = SUB_TOKEN.replace();
                if (value instanceof CustomSet<?> set) set.add(sub);
                else if (value instanceof CustomList<?> list) list.add(sub);
                else if (value instanceof CustomMap<?,?> map) map.add(sub);
                else if (value instanceof OtherSet<?> set) set.add(sub);
                else if (value instanceof OtherList<?> list) list.add(sub);
                else if (value instanceof OtherMap<?,?> map) map.add(sub);
                else throw VariableException.TYPE_ERROR.getThrow(sub);
                yield null;
            }
        };
    }

    private String TYPE = null;

    @Override
    public String getReplace() {
        return switch (SUB) {
            case CLEAR, ADD -> null;
            default -> {
                if (TYPE == null) setType();
                yield TYPE;
            }
        };
    }

    private void setType() {
        TYPE = switch (SUB) {
            case CLEAR, ADD -> null;
            case CONTAINS, CONTAINS_V, IS_EMPTY -> b;
            case MAX, MIN -> switch (PARAMS.getReplace()) {
                case li, si, mi -> i;
                case ll, sl, ml -> l;
                case ld, sd, md -> d;
                case lf, sf, mf -> f;
                case lc, sc, mc -> c;
                default -> throw VariableException.TYPE_ERROR.getThrow(PARAMS.getReplace());
            };
            case SUM -> switch (PARAMS.getReplace()) {
                case li, si, mi, lc, sc, mc -> i;
                case ll, sl, ml -> l;
                case lf, sf, mf -> f;
                case ld, sd, md -> d;
                default -> throw VariableException.TYPE_ERROR.getThrow(PARAMS.getReplace());
            };
            case GET -> switch (PARAMS.getReplace()) {
                case li, si, mi -> i;
                case ll, sl, ml -> l;
                case lb, sb, mb -> b;
                case ls, ss, ms -> s;
                case lf, sf, mf -> f;
                case ld, sd, md -> d;
                case lc, sc, mc, s -> c;
                case KlassToken.LIST -> {
                    if (PARAMS.replace() instanceof OtherList<?> list) yield list.getType();
                    else throw VariableException.TYPE_ERROR.getThrow(PARAMS.replace());
                }
                case KlassToken.MAP -> {
                    if (PARAMS.replace() instanceof OtherMap<?, ?> map) yield map.getValueType();
                    else throw VariableException.TYPE_ERROR.getThrow(PARAMS.replace());
                }
                case KlassToken.SET -> {
                    if (PARAMS.replace() instanceof OtherSet<?> set) yield set.getType();
                    else throw VariableException.TYPE_ERROR.getThrow(PARAMS.replace());
                }
                default -> throw VariableException.TYPE_ERROR.getThrow(PARAMS.getReplace());
            };
        };
    }
}
