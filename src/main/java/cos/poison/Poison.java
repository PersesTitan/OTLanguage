package cos.poison;

import bin.exception.MatchException;
import bin.token.LoopToken;
import work.StartWork;

import java.util.Map;
import java.util.regex.Matcher;

public class Poison implements StartWork, LoopToken {
    private final String httpMethod = orMerge(POISON_POST, POISON_GET);

    private final String pattern1 = blackMerge(httpMethod, "(", BL, "[^"+BL+BR+"]+", BR, ")+", BRACE_STYLE(), RETURN);
//    private final String patternText = startEndMerge(POISON_METHOD, blackMerge);
//    private final Pattern pattern = Pattern.compile(patternText);
    private Matcher matcher;

    @Override
    public boolean check(String line) {
//        return (matcher = pattern.matcher(line)).find();
        return false;
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {
        String[] tokens = line.strip().split(BLANKS, 2); // ㅍㅇㅍ~ㅍㅅㅍ,
        if (tokens.length != 2) throw MatchException.grammarError();
        String[] method = tokens[0].split(ACCESS, 2);   // ㅍㅇㅍ, ㅍㅅㅍ
        if (method.length != 2) throw MatchException.grammarError();

    }
}
