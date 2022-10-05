package bin.orign.loop;

import bin.apply.sys.make.StartLine;
import bin.exception.MatchException;
import bin.token.LoopToken;
import bin.token.cal.BoolToken;
import work.StartWork;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bin.apply.Controller.boolCalculator;
import static bin.apply.Setting.lineStart;

public class While implements StartWork, LoopToken, BoolToken {
    private final String type;
    private final Matcher matcher;

    public While(String type) {
        this.type = type;
        this.matcher =
                Pattern.compile(startEndMerge(type, BLANKS, BOOL, BLANKS, BRACE_STYLE()))
                .matcher("");
    }

    @Override
    public boolean check(String line) {
        return matcher.reset(line).find();
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {
        String[] tokens = line.strip().split("\\s+", 3);
        if (tokens.length != 3) throw MatchException.grammarError();
        String[] totalTokens = bothEndCut(tokens[2]).split(",", 3);
        if (totalTokens.length != 3) throw MatchException.grammarError();
        boolean bool = tokens[1].equals("ㅇㅇ");
        String total = LOOP_TOKEN.get(totalTokens[0]);
        int start = total.indexOf("\n" + totalTokens[1] + " ");
        int end = total.indexOf("\n" + totalTokens[2] + " ");
        total = total.substring(start, end);

        String b = origen.strip()
                .replaceFirst(START + type, "")
                .replaceFirst(BRACE_STYLE() + END, "")
                .strip();

        while (bool) {
            if (StartLine.startLoop(total, totalTokens[0], repositoryArray).equals(LoopToken.BREAK)) break;
            bool = lineStart(b, repositoryArray).equals("ㅇㅇ");
        }
    }
}
