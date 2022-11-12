package etc.loop;

import etc.calculator.token.BlankV3Test;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;

import static bin.apply.sys.make.StartLine.getFinalTotal;
import static bin.apply.sys.make.StartLine.startStartLine;
import static bin.token.LoopToken.LOOP_TOKEN;

public class WhileTest extends StartWorkV3 implements BlankV3Test {
    // 1
    public WhileTest(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        System.out.println("pass");
        int poison = params[0].lastIndexOf("(");
        String start = params[0].substring(0, poison).strip();      // ㅇㅇ ㄸ ㄴㄴ
        String end = params[0].substring(poison).strip();           // (test,10,14)

        StringTokenizer tokenizer = new StringTokenizer(end.substring(1, line.length()-1), ",");
        String fileName = tokenizer.nextToken();
        String total = LOOP_TOKEN.get(fileName);
        int s = total.indexOf("\n" + tokenizer.nextToken() + " ");
        int e = total.indexOf("\n" + tokenizer.nextToken() + " ");

        String finalTotal = getFinalTotal(false, total.substring(s, e), fileName);

        while (getBool(start, repositoryArray)) {
            startStartLine(finalTotal, fileName, repositoryArray);
        }
    }
}
