package bin.apply.line.parser;

import bin.apply.item.ParamItem;
import bin.apply.mode.LoopMode;
import bin.exception.MatchException;
import bin.token.EditToken;
import bin.token.ParserToken;
import bin.token.check.CheckToken;
import bin.token.Token;
import org.apache.arrow.flatbuf.Int;

import java.util.Map;

public interface LoopLine {
    default ParamItem[] splitPut(String endLine) {
        String token = LoopMode.PUT.cut(endLine);
        if (CheckToken.isParams(token)) {
            String[] param = ParserToken.param(EditToken.bothCut(token));
            int size = param.length;
            ParamItem[] items = new ParamItem[size];
            for (int i = 0; i<size; i++) items[i] = makeParam(param[i]);
            return items;
        } else return new ParamItem[]{makeParam(token)};
    }

    private ParamItem makeParam(String line) {
        String[] tokens = EditToken.splitBlank(line);
        if (tokens[0].isEmpty()) throw MatchException.GRAMMAR_ERROR.getThrow(line);
        if (tokens[1].isEmpty()) throw MatchException.GRAMMAR_ERROR.getThrow(line);
        return new ParamItem(tokens[0], tokens[1]);
    }

    default int nextCatch(Map<Integer, String> map, int s, int e) {
        int count = 0;
        for (int i = s+1; i<e; i++) {
            String line = map.get(i);
            if (line.isEmpty()) continue;
            if (CheckToken.endWith(line, Token.LOOP_S)) count++;
            if (CheckToken.startWith(line, Token.LOOP_E) && count-- <= 0)
                throw MatchException.ZONE_MATCH_ERROR.getThrow(line);
            if (CheckToken.bothCheck(map.get(i), Token.LOOP_E, Token.LOOP_S) && count==0) return i;
        }
        throw MatchException.ZONE_MATCH_ERROR.getThrow(map.get(s));
    }

    default int next(Map<Integer, String> map, int start) {
        int count = 1;
        int size = map.size();
        for (int i = start + 1; i < size; i++) {
            String line = map.get(i);
            if (line.isEmpty()) continue;
            if (CheckToken.endWith(line, Token.LOOP_S)) count++;
            if (CheckToken.startWith(line, Token.LOOP_E)) {
                if (count-- <= 0) throw MatchException.ZONE_MATCH_ERROR.getThrow(line);
                if (count == 0) return i;
            }
        }
        throw MatchException.ZONE_MATCH_ERROR.getThrow(map.get(start));
    }

    default boolean checkFor(String line) {
        int start, end;
        return (start = line.indexOf(Token.FOR)) >= 0
                && (end = line.lastIndexOf(Token.FOR)) >= 0
                && start != end;
    }
}
