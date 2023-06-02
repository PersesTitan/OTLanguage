package bin.apply.line.loop;

import bin.apply.Repository;
import bin.apply.item.CodeItem;
import bin.apply.item.ParamItem;
import bin.apply.work.error.ThrowItem;
import bin.apply.line.LineTool;
import bin.apply.line.parser.LoopLine;
import bin.apply.work.ThrowToken;
import bin.exception.Error;
import bin.exception.MatchException;
import bin.exception.VariableException;

import java.util.Map;

public class TryCatchLine implements LineTool, LoopLine {
    private final ParamItem PUT;
    private final CodeItem CODE;
    private final int S, M, E;

    public TryCatchLine(Map<Integer, String> map, int s, int e, CodeItem code, ParamItem[] PUT) {
        if (PUT.length != 1) throw MatchException.PARAM_COUNT_ERROR.getThrow(map.get(e));
        if (!PUT[0].type().equals(ThrowToken.THROW)) throw VariableException.TYPE_ERROR.getThrow(PUT[0].type());
        this.PUT = PUT[0];
        this.CODE = code;
        int m = nextCatch(map, s, e);
        if (s+1 > m) throw MatchException.GRAMMAR_ERROR.getThrow(null);
        if (m+1 > e) throw MatchException.GRAMMAR_ERROR.getThrow(null);
        this.S = s+1;
        this.M = m+1;
        this.E = e;
        map.put(m, "");
    }

    public TryCatchLine(int s, int e, CodeItem code) {
        if (++s > e) throw MatchException.GRAMMAR_ERROR.getThrow(null);
        this.PUT = null;
        this.CODE = code;
        this.S = s;
        this.M = e;
        this.E = e;
    }

    @Override
    public void startItem() {
        try {
            CODE.startTry(S, M);
        } catch (Error e) {
            if (PUT != null) startPut(e);
        }
    }

    private void startPut(Error e) {
        try {
            Repository.repositoryArray.create(PUT, new ThrowItem(e));
            CODE.startTry(M, E);
        } finally {
            Repository.repositoryArray.remove(PUT);
        }
    }

    @Override
    public int start(int line) {
        startItem();
        return E + 1;
    }
}
