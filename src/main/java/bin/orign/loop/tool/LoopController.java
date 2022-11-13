package bin.orign.loop.tool;

import bin.exception.MatchException;
import bin.token.LoopToken;
import bin.token.cal.BoolToken;

public class LoopController implements LoopToken, BoolToken {
    String keyWord = orMerge(BREAK, CONTINUE) + END;
    String patternText = START + blackMerge(BOOL, QUESTION) + BLANK + keyWord;

    public String check(String line) {
        if (line.matches(patternText)) {
            String[] tokens = line.split(BLANK + QUESTION + BLANK, 2);
            if (tokens.length != 2) throw new MatchException().grammarError();    // ㅇㅇ break, continue
            else if (tokens[0].equals(TRUE)) return tokens[1];
            else return "";
        }
        return line;
    }
}
