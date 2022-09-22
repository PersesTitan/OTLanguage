package cos.poison.method;

import bin.exception.MatchException;
import bin.token.LoopToken;
import work.StartWork;

import java.util.Map;
import java.util.regex.Pattern;

public class PoisonGet implements LoopToken, StartWork {
    private final String patternText;
    private final Pattern pattern;

    public PoisonGet(String className) {
        String or = orMerge(TOTAL_LIST) + BLANKS + VARIABLE_NAME + VARIABLE_PUT + "\\S+";
        this.patternText = startEndMerge(
                className, ACCESS, POISON_GET, BLANKS,
                "[" + ARGUMENT + "]", blackMerge("(", or, ")*"),
                BLANKS, BRACE_STYLE(), BLANKS, RETURN);
        this.pattern = Pattern.compile(this.patternText);
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {
        // ㅍㅇㅍ~ㅍㅅㅍ, [sub/][ㅇㅅㅇ ㅁ:ㅁ] (start,1,10) => index.html
        String[] tokens = line.strip().split(BLANKS, 2);
        if (tokens.length != 2) throw MatchException.grammarError();
        String token = tokens[1];
    }
}
