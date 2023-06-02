package bin.apply.line.loop;

import bin.apply.Repository;
import bin.apply.item.CodesItem;
import bin.apply.item.ParamItem;
import bin.apply.line.LineTool;
import bin.parser.param.ParamToken;
import bin.parser.param.ParserParamItem;
import work.LoopWork;

public class StaticLoopLine implements LineTool {
    private final ParamToken[] PARAMS;
    private final LoopWork WORK;
    private final ParamItem[] ITEM;
    private final CodesItem CODE;

    public StaticLoopLine(String klass, String method, String[] params, ParamItem[] item, CodesItem code) {
        int size = params.length;
        this.WORK = Repository.loopWorks.get(klass, method, size);
        this.PARAMS = ParserParamItem.startParam(size, WORK, params);
        this.ITEM = item;
        this.CODE = code;
    }

    @Override
    public void startItem() {
        WORK.loop(null, PARAMS, CODE, ITEM);
    }

    @Override
    public int start(int line) {
        this.startItem();
        return CODE.END() + 1;
    }
}
