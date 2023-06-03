package bin.apply.line.loop;

import bin.apply.item.CodeItem;
import bin.apply.item.CodesItem;
import bin.apply.line.LineTool;
import bin.exception.MatchException;
import bin.parser.param.ParamToken;
import bin.parser.param.ParserParamItem;
import bin.token.CastingToken;
import bin.token.EditToken;
import bin.token.Token;
import bin.token.check.CheckToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IfLine implements LineTool {
    private final IfItem[] ITEMS;
    private final CodesItem ELSE;
    private final int END;

    public IfLine(Map<Integer, String> map, int start, int end, CodeItem code) {
        List<Integer> list = new ArrayList<>();
        list.add(start);
        int count = 0;
        for (int i = start+1; i<end; i++) {
            String line = map.get(i);
            if (line.isEmpty()) continue;
            if (CheckToken.endWith(line, Token.LOOP_S)) count++;    // (...) {
            if (CheckToken.startWith(line, Token.LOOP_E)) count--;  // } (...)
            if (count < 0) throw MatchException.ZONE_MATCH_ERROR.getThrow(line);
            if (count == 0 && CheckToken.bothCheck(line, Token.LOOP_E, Token.LOOP_S)) list.add(i);
        }
        list.add(end);

        int size = list.size()-1;
        List<CodesItem> CODES = new ArrayList<>();
        List<String> TOKEN = new ArrayList<>();
        for (int i = 0; i<size; i++) {
            int p = list.get(i);
            CODES.add(new CodesItem(p, list.get(i+1), code));
            if (i == 0) {
                String line = EditToken.bothCut(map.get(p), 3, 1).strip();
                if (line.isEmpty()) throw MatchException.GRAMMAR_ERROR.getThrow(line);
                else TOKEN.add(line);
            } else TOKEN.add(EditToken.bothCut(map.get(p)).strip());
            map.put(p, "");
        }
        if (TOKEN.get(size-1).isEmpty()) {
            this.ELSE = CODES.remove(size-1);
            TOKEN.remove(size-1);
        } else this.ELSE = null;

        List<IfItem> ITEMS = new ArrayList<>();
        size = CODES.size();
        for (int i = 0; i<size; i++) {
            String line = TOKEN.get(i);
            if (CheckToken.isParams(line)) line = EditToken.bothCut(line);
            ParamToken token = new ParserParamItem(line).start();
            ITEMS.add(new IfItem(token, CODES.get(i)));
        }
        this.ITEMS = ITEMS.toArray(IfItem[]::new);
        this.END = end + 1;
    }

    @Override
    public void startItem() {
        for (IfItem item : ITEMS) {
            if (CastingToken.getBoolean(item.TOKEN.replace())) {
                item.CODE.start();
                return;
            }
        }
        if (ELSE != null) ELSE.start();
    }

    @Override
    public int start(int line) {
        startItem();
        return END;
    }

    private record IfItem(ParamToken TOKEN, CodesItem CODE){}
}
