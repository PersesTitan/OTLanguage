package bin.apply.line.loop;

import bin.apply.item.CodesItem;
import bin.apply.line.LineTool;
import bin.parser.param.ParamToken;
import bin.token.CastingToken;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ForLine implements LineTool {
    private final ParamToken A, B, C;
    private final CodesItem CODE;

    @Override
    public void startItem() {
        double a = CastingToken.getDouble(A.replace());
        double b = CastingToken.getDouble(B.replace());
        double c = CastingToken.getDouble(C.replace());
        for (double i = a; i < b; i+=c) CODE.start();
    }

    @Override
    public int start(int line) {
        startItem();
        return CODE.END() + 1;
    }
}
