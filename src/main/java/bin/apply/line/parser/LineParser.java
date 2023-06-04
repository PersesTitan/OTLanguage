package bin.apply.line.parser;

import bin.apply.item.CodeItem;
import bin.apply.item.CodesItem;
import bin.apply.item.ParamItem;
import bin.apply.item.ShellCodeItem;
import bin.apply.line.LineTool;
import bin.apply.line.loop.*;
import bin.apply.line.variable.CreateHpLine;
import bin.apply.line.variable.CreateLine;
import bin.apply.line.variable.UpdateLine;
import bin.apply.mode.LoopMode;
import bin.apply.work.system.Import;
import bin.apply.work.SystemToken;
import bin.exception.Error;
import bin.exception.MatchException;
import bin.parser.param.ParamToken;
import bin.parser.param.ParserParamItem;
import bin.token.*;
import bin.token.check.CheckToken;
import lombok.Getter;

import java.util.*;

public class LineParser implements LoopLine, DefineLine, KlassToken {
    @Getter private final LineTool[] LINES;
    @Getter private final String[] FILES;
    private final CodeItem CODE;
    private final Map<Integer, String> codes;
    protected int start = 0;

    public LineParser(String path, Map<Integer, String> codes) {
        final int SIZE = codes.size();
        this.LINES = new LineTool[SIZE];
        this.codes = codes;
        this.FILES = new String[SIZE];
        codes.forEach((k, v) -> FILES[k] = v.isEmpty() || CheckToken.startWith(v, Token.REMARK) ? null : v);
        this.CODE = new CodeItem(path, FILES, LINES);
        for (; start < SIZE; start++) LINES[start] = this.parser(SYSTEM, start);
    }

    protected LineParser(Map<Integer, String> codes, ShellCodeItem CODE) {
        this.CODE = CODE;
        this.codes = codes;
        this.LINES = null;
        this.FILES = null;
    }

    private LineParser() {
        this.LINES = null;
        this.FILES = null;
        this.CODE = null;
        this.codes = null;
    }

    // create LineTool only line
    public static LineTool createParser(String line) {
        return new LineParser().parser(line);
    }

    protected LineTool loopPut(String REPO_KLASS, int s, int e, LineTool tool) {
        try {
            return tool;
        } finally {
            this.LINES[e] = null;
            this.start = e;
            for (int i = s+1; i < e; i++) this.LINES[i] = this.parser(REPO_KLASS, i);
        }
    }

    public LineTool parser(String REPO_KLASS, int start) {
        String line = this.codes.get(start);
        if (line.isEmpty() || CheckToken.startWith(line, Token.REMARK)) return null;
        if (CheckToken.startWith(line, Token.LOOP_E)) {
            if (CheckToken.endWith(line, Token.LOOP_S)) throw MatchException.ZONE_MATCH_ERROR.getThrow(line);
            else return null;
        } else if (CheckToken.endWith(line, Token.LOOP_S)) {
            // 루프 '{'
            line = EditToken.bothCut(line, 0, 1).strip();
            int end = next(this.codes, start);
            String endLine = this.codes.get(end);
            LoopMode mode = LoopMode.getLoop(endLine);
            // for 문과 문법이 일치할때
            if (checkFor(line)) {
                StringTokenizer tokenizer = new StringTokenizer(line, Character.toString(Token.FOR));
                if (tokenizer.countTokens() == 3) {
                    ParamToken A = new ParserParamItem(tokenizer.nextToken().strip()).start();
                    ParamToken B = new ParserParamItem(tokenizer.nextToken().strip()).start();
                    ParamToken C = new ParserParamItem(tokenizer.nextToken().strip()).start();
                    return switch (mode) {
                        case NONE -> {
                            LineTool tool = new ForLine(A, B, C, new CodesItem(start, end, CODE));
                            yield loopPut(null, start, end, tool);
                        }
                        case PUT -> {
                            String[] tokens = EditToken.splitBlank(LoopMode.PUT.cut(endLine));
                            String TYPE = tokens[0].strip(), NAME = tokens[1].strip();
                            CodesItem item = new CodesItem(start, end, CODE);
                            LineTool tool = new ForPutLine(A, B, C, item, TYPE, NAME);
                            yield loopPut(null, start, end, tool);
                        }
                        default -> throw MatchException.GRAMMAR_ERROR.getThrow(endLine);
                    };
                } else throw MatchException.GRAMMAR_ERROR.getThrow(line);
            }

            // ㅁㅅㅁ 메소드명[타입 이름] => [ㅁㅅㅁ, 메소드명[타입 이름]]
            String[] km = EditToken.splitBlank(line);
            return switch (km[0]) {
                case KLASS -> {
                    CodesItem codesItem = new CodesItem(start, end, CODE);
                    String[] tokens = this.getTokens(km[1]);
                    defineKlass(REPO_KLASS, tokens[0], tokens[1], codesItem, mode);
                    yield loopPut(tokens[0], start, end, new JumpLine(end));
                }
                case METHOD -> {
                    boolean isStatic = Objects.equals(REPO_KLASS, SYSTEM);
                    CodesItem codesItem = new CodesItem(start, end, CODE);
                    defineMethod(REPO_KLASS, isStatic, km[1], codesItem, mode, endLine);
                    yield loopPut(null, start, end, new JumpLine(end));
                }
                case STATIC_METHOD -> {
                    CodesItem codesItem = new CodesItem(start, end, CODE);
                    defineMethod(REPO_KLASS, true, km[1], codesItem, mode, endLine);
                    yield loopPut(null, start, end, new JumpLine(end));
                }
                case SystemToken.IF -> loopPut(null, start, end, new IfLine(this.codes, start, end, CODE));
                case SystemToken.TRY_CATCH -> {
                    if (!km[1].isEmpty()) throw MatchException.PARAM_COUNT_ERROR.getThrow(km[1]);
                    yield switch (mode) {
                        case NONE -> loopPut(REPO_KLASS, start, end, new TryCatchLine(start, end, CODE));
                        case PUT -> {
                            ParamItem[] items = splitPut(endLine);
                            TryCatchLine tool = new TryCatchLine(codes, start, end, CODE, items);
                            yield loopPut(REPO_KLASS, start, end, tool);
                        }
                        default -> throw MatchException.GRAMMAR_ERROR.getThrow(endLine);
                    };
                }
                default -> {
                    CodesItem codesItem = new CodesItem(start, end, CODE);
                    yield switch (mode) {
                        case PUT -> {
                            ParamItem[] items = splitPut(endLine);
                            LineTool tool = new ParserParamItem(line, true).getLoop(codesItem, items);
                            yield loopPut(null, start, end, tool);
                        }
                        case NONE -> {
                            String[] tokens = ParserToken.paramParser(line);
                            LineTool tool;
                            if (SystemToken.IF.equals(tokens[0])) tool = new IfLine(this.codes, start, end, CODE);
                            else tool = new ParserParamItem(line, true).getLoop(codesItem);
                            yield loopPut(null, start, end, tool);
                        }
                        default -> throw MatchException.GRAMMAR_ERROR.getThrow(endLine);
                    };
                }
            };
        }
        return parser(line);
    }

    private LineTool parser(String line) {
        if (line.indexOf(Token.PUT)>=0) {
            // update variable
            String[] tokens = EditToken.split(line, Token.PUT);
            int access = EditToken.getAccess(tokens[0]);
            if (CheckToken.isName(tokens[0].substring(access)))
                return new UpdateLine(tokens[0], tokens[1]);
            // create variable
            tokens = EditToken.split(line, Token.PUT);
            Stack<String> VARIABLE = null;
            try {
                VARIABLE = ParserToken.parser(tokens[0]); // [ㅇㅁㅇ, 변수명], [ㅇㅁㅇ, [1], 변수명]
            } catch (Error ignored) {}
            if (VARIABLE != null) {
                int size = VARIABLE.size();
                if (size == 2) {
                    String name = VARIABLE.pop(), klass = VARIABLE.pop();  // 변수명, 타입
                    return new CreateLine(klass, name, tokens[1].strip());
                } else if (size == 3 && CheckToken.isParams(VARIABLE.get(1))) {
                    // [ㅇㅁㅇ, [1], 변수명]
                    String hpToken = EditToken.bothCut(VARIABLE.remove(1));
                    String name = VARIABLE.pop(), klass = VARIABLE.pop();
                    return new CreateHpLine(klass, name, tokens[1].strip(), hpToken);
                }
            }
        }
        String[] tokens = ParserToken.paramParser(line);
        if (tokens.length == 2 && Objects.equals(tokens[0], IMPORT)) {
            if (CheckToken.isParams(tokens[1])) new Import(EditToken.bothCut(tokens[1]));
            else new Import(tokens[1].strip());
            return null;
        } else return new ParserParamItem(line).getMethod();
    }
}
