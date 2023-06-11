package bin.parser.param;

import bin.apply.Repository;
import bin.apply.calculator.bool.CalculatorBool;
import bin.apply.calculator.bool.CalculatorCompare;
import bin.apply.calculator.bool.CalculatorNot;
import bin.apply.calculator.number.CalculatorNumber;
import bin.apply.calculator.number.NumberToken;
import bin.apply.item.CodesItem;
import bin.apply.item.ParamItem;
import bin.apply.line.LineTool;
import bin.apply.line.loop.LoopLine;
import bin.apply.line.loop.StaticLoopLine;
import bin.apply.mode.TypeMode;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.parser.param.fixation.ParamCharItem;
import bin.parser.param.fixation.ParamStringItem;
import bin.parser.param.item.*;
import bin.parser.param.sub.ParamSubItem;
import bin.parser.param.variable.ParamArrayListItem;
import bin.parser.param.variable.ParamArrayMapItem;
import bin.parser.param.variable.ParamArraySetItem;
import bin.token.*;
import bin.token.check.CheckToken;
import work.CreateWork;
import work.MethodWork;
import work.WorkTool;

import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class ParserParamItem implements ParserParamTool, KlassToken {
    private final Stack<String> TOKENS;
    private final boolean isLoop;
    private final String LINE;

    public ParserParamItem(String line) {
        this(line, false);
    }

    public ParserParamItem(String line, boolean isLoop) {
        this.LINE = line;
        this.isLoop = isLoop;
        boolean startVar = false;
        if (CheckToken.startWith(line, Token.REPLACE_S)) {
            line = line.substring(1);
            startVar = true;
        }
        int access = EditToken.getAccess(line);
        Stack<String> stack = parser(access, line.substring(access));
        if (stack.size() == 0) throw MatchException.GRAMMAR_ERROR.getThrow(line);
        else if (stack.contains("")) throw VariableException.ACCESS_ERROR.getThrow(line);
        Collections.reverse(stack); // 순서 반대로 만들기
        if (access != 0) stack.add(ACCESS.repeat(access) + stack.pop());
        if (startVar) stack.add(Token.REPLACE_S + stack.pop());
        this.TOKENS = stack;
    }

    private ParamToken getPop(String pop) {
        if (CheckToken.isSet(pop)) return new ParamArraySetItem(pop);
        else if (CheckToken.isList(pop)) return new ParamArrayListItem(pop);
        else if (CheckToken.isMap(pop)) return new ParamArrayMapItem(pop);
        else if (CalculatorBool.check(pop)) return new CalculatorBool(pop);
        else if (CalculatorNot.check(pop)) return new CalculatorNot(pop);
        else if (CalculatorCompare.check(pop)) return new CalculatorCompare(pop);
        else if (NumberToken.check(pop)) return new CalculatorNumber(pop);
        else if (pop.length()>1 && CheckToken.bothCheck(pop, '"', '"')) return new ParamStringItem(pop);
        else if (pop.length()>1 && CheckToken.bothCheck(pop, '\'', '\'')) return new ParamCharItem(pop);
        else return ParamSubItem.create(null, pop);
    }

    public LineTool getLoop(CodesItem CODE, ParamItem... PUT) {
        ParamToken token;
        switch (TOKENS.size()) {
            case 1 -> {
                String klass = KlassToken.DEFAULT_KLASS.get();
                String[] tokens = ParserToken.paramParser(TOKENS.pop());
                String[] params = ParserToken.param(tokens[1]);
                return new StaticLoopLine(klass, tokens[0], params, PUT, CODE);
            }
            case 2 -> {
                if (CheckToken.isKlass(TOKENS.peek())) {
                    String klass = TOKENS.pop();
                    String[] tokens = ParserToken.paramParser(TOKENS.pop());
                    String[] params = ParserToken.param(tokens[1]);
                    return new StaticLoopLine(klass, tokens[0], params, PUT, CODE);
                } else token = start();
            }
            default -> token = start();
        }
        String[] tokens = ParserToken.paramParser(TOKENS.pop());
        String[] params = ParserToken.param(tokens[1]);
        return new LoopLine(token, params, tokens[0], CODE, PUT);
    }

    public LineTool getMethod() {
        return start()::replace;
    }

    public ParamToken start() {
        String pop = TOKENS.pop();
        ParamToken TOKEN = getPop(pop);
        if (TOKEN == null) {
            String[] tokens = ParserToken.paramParser(pop);
            if (CheckToken.startWith(tokens[0], Token.REPLACE_S)) {
                // 강제 변수 설정
                if (tokens[1].isEmpty()) TOKEN = new ParamVariableItem(tokens[0].substring(1));
                else throw MatchException.GRAMMAR_ERROR.getThrow(LINE);
            } else if (tokens[1].isEmpty()) {
                TypeMode TYPE = CheckToken.checkType(tokens[0]);
                if (!(TYPE.equals(TypeMode.STRING) || TYPE.equals(TypeMode.CHARACTER))) {
                    TOKEN = ParserParamTool.start(TYPE, tokens[0]);
                } else if (CheckToken.isKlass(tokens[0])) {
                    String[] methods = ParserToken.paramParser(TOKENS.pop());
                    String[] params = ParserToken.param(methods[1]);
                    int size = params.length;
                    MethodWork work = Repository.methodWorks.get(tokens[0], methods[0], size);
                    TOKEN = new ParamStaticItem(work, startParam(size, work, params));
                } else if (Repository.methodWorks.hasStatic(tokens[0])) {
                    String klass = KlassToken.DEFAULT_KLASS.get();
                    MethodWork work = Repository.methodWorks.get(klass, tokens[0], 0);
                    TOKEN = new ParamStaticItem(work, new ParamToken[0]);
                } else TOKEN = new ParamVariableOrStringItem(tokens[0]);
            } else if (CheckToken.isKlass(tokens[0])) {
                String[] params = ParserToken.param(tokens[1]);
                CreateWork<?> work = Repository.createWorks.get(tokens[0]);
                TOKEN = new ParamCreateItem(tokens[0], work, startParam(params.length, work, params));
            } else if (Repository.methodWorks.hasStatic(tokens[0])) {
                String klass = KlassToken.DEFAULT_KLASS.get();
                String[] params = ParserToken.param(tokens[1]);
                int size = params.length;
                MethodWork work = Repository.methodWorks.get(klass, tokens[0], size);
                TOKEN = new ParamStaticItem(work, startParam(size, work, params));
            } else TOKEN = new ParamVariableOrStringItem(tokens[0]);
        }

        if (isLoop) {
            if (TOKENS.size() < 1) throw MatchException.GRAMMAR_ERROR.getThrow(LINE);
            while (TOKENS.size() > 1) {
                ParamSubItem SUB = ParamSubItem.create(TOKEN, TOKENS.peek());
                if (SUB == null) TOKEN = ParserParamItem.other(TOKEN, TOKENS.pop());
                else {
                    TOKENS.pop();
                    TOKEN = SUB;
                }
            }
        } else {
            while (!TOKENS.isEmpty()) {
                ParamSubItem SUB = ParamSubItem.create(TOKEN, TOKENS.peek());
                if (SUB == null) TOKEN = ParserParamItem.other(TOKEN, TOKENS.pop());
                else {
                    TOKENS.pop();
                    TOKEN = SUB;
                }
            }
        }

        return TOKEN;
    }

    public static ParamToken other(ParamToken TOKEN, String line) {
        String[] tokens = ParserToken.paramParser(line);
        String[] params = ParserToken.param(tokens[1]);
        return new ParamMethodItem(TOKEN, tokens[0], params);
    }

    public static ParamToken[] startParam(String klass, String paramsToken, boolean isList) {
        CreateWork<?> work = Repository.createWorks.get(klass);
        String[] params = ParserToken.param(paramsToken, isList);
        return startParam(params.length, work, params);
    }

    public static ParamToken[] startParam(int size, WorkTool work, String[] params) {
        String[] types = work.getPARAMS();
        ParamToken[] PARAMS = new ParamToken[size];
        for (int i = 0; i<size; i++) PARAMS[i] = ParserParamTool.start(types[i], params[i]);
        return PARAMS;
    }
}
