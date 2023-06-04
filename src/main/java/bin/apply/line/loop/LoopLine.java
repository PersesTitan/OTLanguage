package bin.apply.line.loop;

import bin.apply.Repository;
import bin.apply.item.CodesItem;
import bin.apply.item.ParamItem;
import bin.apply.line.LineTool;
import bin.parser.param.ParamToken;
import bin.parser.param.ParserParamItem;
import lombok.RequiredArgsConstructor;
import work.LoopWork;

import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor
public class LoopLine implements LineTool {
    private final ParamToken TOKEN;
    private final String[] VALUES;
    private final String METHOD;
    private final CodesItem CODE;
    private final ParamItem[] PUT;

    private String REPLACE_TYPE = null;
    private LoopWork WORK = null;
    private ParamToken[] PARAMS = null;

    private void setWork() {
        if (WORK == null || !Objects.equals(TOKEN.getReplace(), REPLACE_TYPE)) {
            int size = VALUES.length;
            this.REPLACE_TYPE = TOKEN.getReplace();
            this.WORK = Repository.loopWorks.get(REPLACE_TYPE, METHOD, size);
            this.PARAMS = ParserParamItem.startParam(size, WORK, VALUES);
        }
    }

    @Override
    public void startItem() {
        setWork();
        WORK.loop(TOKEN.replace(), PARAMS, CODE, PUT);
    }

    @Override
    public int start(int line) {
        this.startItem();
        return CODE.END() + 1;
    }
}
