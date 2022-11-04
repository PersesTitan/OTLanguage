package bin.apply.sys.run;

import bin.token.MergeToken;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.apply.sys.make.StartLine.getFinalTotal;
import static bin.apply.sys.make.StartLine.startStartLine;

public class TryCatch extends StartWorkV3 implements MergeToken {
    // 1
    public TryCatch(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String[] loopTotals = getLoopTotal(params[0].strip()); // test, total
        try {
            String finalTotal = getFinalTotal(false, loopTotals[1], loopTotals[0]);
            startStartLine(finalTotal, loopTotals[0], repositoryArray);
        } catch (Exception ignored) {}
    }
}
