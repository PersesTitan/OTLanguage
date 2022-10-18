package cos.poison.method;

import cos.poison.Poison;
import work.StartWork;

import java.util.Map;
import java.util.regex.Pattern;

public class PoisonPost implements PoisonTools, StartWork {
    private final String text;
    private final Pattern pattern;

    public PoisonPost(String className) {
        String or = blackMerge(BL, orMerge(TOTAL_LIST) + BLANKS + VARIABLE_NAME + VARIABLE_PUT + "\\S+", BR);
        this.text = className + ACCESS + POISON_POST;
        String patternText = startEndMerge(
                text,
                BL, "[^", BL, BR, "]+", BR, "(", or, ")*",
                BLANKS, BRACE_STYLE, BLANKS, RETURN);
        this.pattern = Pattern.compile(patternText);
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {
        // 1. ㅍㅇㅍ~ㅍㅅㅍ, [sub/][ㅇㅅㅇ ㅁ:ㅁ] (start,1,10) => index.html
        // 2. [sub/][ㅇㅅㅇ ㅁ:ㅁ] (start,1,10) => index.html
        // 3. [sub/][ㅇㅅㅇ ㅁ:ㅁ], (start,1,10) => index.html
        String[] tokens = matchSplitError(
                matchSplitError(
                        line.strip(), text, 2)[1],
                "(?!" + BR + ")" + BLANKS + "(?=" + SL + ")", 2);
        String[] token = bothEndCut(tokens[0]).split(BR + BL); // [sub/][ㅇㅅㅇ ㅁ:ㅁ]
        // (start,1,10), index.html
        String[] total = matchSplitError(tokens[1], BLANK + RETURN_TOKEN + BLANK, 2);

        Poison.httpServerManager.addPost(token[0], getTotal(total[0]), getParams(token), total[1]);
    }
}