package etc.v3.loop;

import bin.exception.MatchException;
import bin.token.LoopToken;
import bin.token.MergeToken;
import etc.v3.calculator.token.BlankV3Test;
import work.v3.StartWorkV3;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static bin.apply.sys.make.StartLine.getFinalTotal;
import static bin.apply.sys.make.StartLine.startStartLine;
import static bin.exception.MatchException.GrammarTypeClass.*;
import static bin.token.LoopToken.BRACE_STYLE;
import static bin.token.LoopToken.LOOP_TOKEN;

public class IfTest extends StartWorkV3 implements MergeToken, BlankV3Test {
    // 1
    public IfTest(int... counts) {
        super(counts);
    }

    private final String ELSE_IF = LoopToken.ELSE_IF.replace("\\", "");
    private final String ELSE = LoopToken.ELSE.replace("\\", "");

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        // ㅇㅇ (test,1,2) ?ㅈ? ㅇㅇ (test,3,4) ?ㅉ? (test,15,16)
        // [ㅇㅇ (test,1,2),  ?ㅈ? ㅇㅇ (test,3,4),  ?ㅉ? (test,15,16)]
        LinkedList<String> list = Pattern.compile(getNoMatchFront(BRACE_STYLE))
                .splitAsStream(params[0].strip())
                .map(String::strip)
                .collect(Collectors.toCollection(LinkedList::new));

        // if
        String start = list.removeFirst();
        int poison = start.lastIndexOf('(');
        String boolToken = start.substring(0, poison);          // ㅇㅇ ㄸ ㅇㅇ
        String loopToken = start.substring(poison).strip();     // (test,15,100)

        // else
        final String end = list.getLast().startsWith(ELSE) ? getEndLoop(list.removeLast()) : null;
        if (getBool(boolToken, repositoryArray)) {
            loopStart(loopToken, repositoryArray);
            return;
        } else if (!list.isEmpty()) {
            for (String lists : list) {
                if (!lists.startsWith(ELSE_IF)) throw new MatchException().grammarTypeError(lists, NO_ELSE_IF);
                // ㅇㅇ (test,3,4)
                start = lists.substring(ELSE_IF.length()).strip();
                poison = start.lastIndexOf('(');
                boolToken = start.substring(0, poison);          // ㅇㅇ ㄸ ㅇㅇ
                loopToken = start.substring(poison).strip();     // (test,15,100)
                if (getBool(boolToken, repositoryArray)) {
                    loopStart(loopToken, repositoryArray);
                    return;
                }
            }
        }
        if (end != null) loopStart(end, repositoryArray);
    }

    // return (test,1,2)
    private String getEndLoop(String line) {
        return line.substring(line.lastIndexOf('('));
    }

    // line = (test,10,15)
    private void loopStart(String line, LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        try {
            StringTokenizer tokenizer = new StringTokenizer(bothEndCut(line), ",");
            String fileName = tokenizer.nextToken();
            String total = LOOP_TOKEN.get(fileName);
            int s = total.indexOf("\n" + tokenizer.nextToken() + " ");
            int e = total.indexOf("\n" + tokenizer.nextToken() + " ");

            String finalTotal = getFinalTotal(false, total.substring(s, e), fileName);

            startStartLine(finalTotal, fileName, repositoryArray);
        } catch (NoSuchElementException e) {
            throw new MatchException().grammarTypeError(line, VALID);
        }
    }
}
