package bin.apply.sys.run;

import bin.apply.sys.make.StartLine;
import bin.exception.MatchException;
import bin.token.LoopToken;
import work.StartWork;

import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bin.apply.sys.make.StartLine.getFinalTotal;
import static bin.apply.sys.make.StartLine.startStartLine;

public class TryCatch implements StartWork, LoopToken {
    private final Matcher matcher;

    public TryCatch(String type) {
        this.matcher = Pattern
                .compile(startEndMerge(type, BLANKS, BRACE_STYLE))
                .matcher("");
    }

    @Override
    public boolean check(String line) {
        return matcher.reset(line).find();
    }

    @Override
    public void start(String line, String origen,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        // ㅠㅅㅠ (test,1,10)
        StringTokenizer tokenizer = new StringTokenizer(line);
        tokenizer.nextToken();
        String tokens = tokenizer.nextToken();
        if (tokens == null) throw MatchException.grammarError();
        String[] loopTotals = getLoopTotal(tokens); // test, total
        try {
            String finalTotal = getFinalTotal(false, loopTotals[1], loopTotals[0]);
            startStartLine(finalTotal, loopTotals[0], repositoryArray);
        } catch (Exception ignored) {}
    }

    @Override
    public void first() {

    }
}
