package bin.apply.line.parser;

import bin.apply.Repository;
import bin.apply.define.DefineKlass;
import bin.apply.define.MethodReplace;
import bin.apply.define.MethodStart;
import bin.apply.item.CodesItem;
import bin.apply.item.ParamItem;
import bin.apply.mode.LoopMode;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.check.CheckToken;
import bin.token.EditToken;
import bin.token.KlassToken;
import bin.token.Token;
import work.MethodWork;

import java.util.*;

interface DefineLine {
    default void defineMethod(String repoKlass, boolean isStatic, String token, CodesItem code, LoopMode mode, String endLine) {
        if (repoKlass == null) throw MatchException.CREATE_METHOD_ERROR.getThrow(null);
        String[] tokens = getTokens(token); // 메소드[ㅇㅁㅇ 변수명] => [메소드, [ㅇㅁㅇ 변수명]]
        final String method = tokens[0], param = tokens[1];
        if (CheckToken.isParams(param)) {
            ParamsItem paramsItem = new ParamsItem(param);
            MethodWork work = switch (mode) {
                case NONE -> new MethodStart(repoKlass, isStatic, paramsItem.param, code);
                case RETURN -> {
                    String endToken = mode.cut(endLine);
                    if (endToken.indexOf(Token.REPLACE_S)>=0) {
                        String[] ts = EditToken.split(endLine, Token.REPLACE_S);
                        yield new MethodReplace(repoKlass, isStatic, code, paramsItem.param, ts[0], ts[1]);
                    } else throw MatchException.GRAMMAR_ERROR.getThrow(endLine);
                }
                default -> throw MatchException.GRAMMAR_ERROR.getThrow(null);
            };
            Repository.methodWorks.put(repoKlass, method, work);
        } else throw MatchException.GRAMMAR_ERROR.getThrow(param);
    }

    default void defineKlass(String repoKlass, String klass, String param, CodesItem code, LoopMode mode) {
        if (!Objects.equals(repoKlass, KlassToken.SYSTEM)) throw MatchException.CREATE_KLASS_ERROR.getThrow(repoKlass);
        if (CheckToken.isParams(param)) {
            ParamsItem paramsItem = new ParamsItem(param);
            DefineKlass defineKlass = new DefineKlass(klass, code, paramsItem.param);
            if (mode.equals(LoopMode.NONE)) Repository.createWorks.put(klass, defineKlass);
            else throw MatchException.GRAMMAR_ERROR.getThrow(null);
        } else throw MatchException.GRAMMAR_ERROR.getThrow(param);
    }

    // ex1) ㅇㅁㅇ 값               => [ㅇㅁㅇ,  값]
    // ex2) ㅇㅁㅇ~ㅁㅅㅁ[값1][값2]    => [ㅇㅁㅇ~ㅁㅅㅁ, [값1][값2]]
    default String[] getTokens(String line) {
        int i;
        if ((i = line.indexOf(Token.PARAM_S))>=0) return new String[] {
                line.substring(0, i),
                line.substring(i)
        };
        else throw MatchException.GRAMMAR_ERROR.getThrow(line);
    }

    class ParamsItem {
        private final ParamItem[] param;

        ParamsItem(String line) {
            StringTokenizer tokenizer = new StringTokenizer(EditToken.bothCut(line), Token.PARAM);
            this.param = new ParamItem[tokenizer.countTokens()];
            int i = 0;

            Set<String> set = new HashSet<>();
            while (tokenizer.hasMoreTokens()) {
                String[] tokens = tokenizer.nextToken().split("\\s+");
                String name = tokens[1];
                if (set.contains(name)) throw VariableException.DEFINE_NAME.getThrow(name);
                else set.add(name);
                this.param[i++] = new ParamItem(tokens[0], name);
            }
        }
    }
}
