package bin.orign.loop;

import bin.apply.sys.make.StartLine;
import bin.calculator.tool.Calculator;
import bin.exception.MatchException;
import bin.token.LoopToken;
import bin.token.MergeToken;
import work.v3.StartWorkV3;

import java.util.*;

import static bin.apply.sys.make.StartLine.getFinalTotal;
import static bin.calculator.tool.Calculator.*;
import static bin.exception.MatchException.GrammarTypeClass.VALID;
import static bin.token.LoopToken.LOOP_TOKEN;

public class While extends StartWorkV3 implements Calculator, MergeToken {
    // 1
    public While(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        int poison = params[0].lastIndexOf('(');
        String start = params[0].substring(0, poison);              // ㅇㅇ ㄸ ㄴㄴ
        String end = params[0].substring(poison).strip();           // (test,10,14)

        try {
            StringTokenizer tokenizer = new StringTokenizer(bothEndCut(end), ",");
            String fileName = tokenizer.nextToken();
            String total = LOOP_TOKEN.get(fileName);
            int s = total.indexOf("\n" + tokenizer.nextToken() + " ");
            int e = total.indexOf("\n" + tokenizer.nextToken() + " ");

            String finalTotal = getFinalTotal(false, total.substring(s, e), fileName);
            while (getBool(start, repositoryArray)) {
                if (Objects.equals(StartLine.startLoop(finalTotal, fileName, repositoryArray), LoopToken.BREAK)) break;
            }
        } catch (NoSuchElementException e) {
            throw new MatchException().grammarTypeError(line, VALID);
        }
    }
}
