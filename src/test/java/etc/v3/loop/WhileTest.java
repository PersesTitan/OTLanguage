package etc.v3.loop;

import bin.exception.MatchException;
import bin.token.MergeToken;
import etc.v3.calculator.token.BlankV3Test;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import static bin.apply.sys.make.StartLine.getFinalTotal;
import static bin.apply.sys.make.StartLine.startStartLine;
import static bin.exception.MatchException.GrammarTypeClass.*;
import static bin.token.LoopToken.LOOP_TOKEN;

public class WhileTest extends StartWorkV3 implements BlankV3Test, MergeToken {
    // 1
    public WhileTest(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) throws MatchException {
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
                startStartLine(finalTotal, fileName, repositoryArray);
            }
        } catch (NoSuchElementException e) {
            throw new MatchException().grammarTypeError(line, VALID);
        }
    }
}
