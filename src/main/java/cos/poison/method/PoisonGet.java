package cos.poison.method;

import bin.apply.Setting;
import bin.exception.FileException;
import bin.exception.MatchException;
import bin.token.LoopToken;
import work.StartWork;

import java.io.File;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bin.apply.sys.item.Separator.SEPARATOR_FILE;

public class PoisonGet implements LoopToken, StartWork {
    private final String patternText;
    private final Pattern pattern;
    private final String param;
    private final Pattern paramPattern;

    public PoisonGet(String className) {
        String or = blackMerge(BL, orMerge(TOTAL_LIST) + BLANKS + VARIABLE_NAME + VARIABLE_PUT, BR);
        this.param = "[" + ARGUMENT + "]" + "(" + or + ")*";
        this.patternText = startEndMerge(
                className, ACCESS, POISON_GET, BLANKS,
                param,
                BLANKS, BRACE_STYLE(), BLANKS, RETURN);

        this.pattern = Pattern.compile(this.patternText);
        this.paramPattern = Pattern.compile(START + param);
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
        String token = tokens[1]; // [sub/][ㅇㅅㅇ ㅁ:ㅁ] (start,1,10) => index.html
        Matcher param = this.paramPattern.matcher(token);
        if (param.find()) {
            String group = param.group(); // [sub/][ㅇㅅㅇ ㅁ:ㅁ]
            String[] values = token.strip().split(BLANK + RETURN_TOKEN + BLANK, 2);
            if (values.length != 2) throw MatchException.grammarError();
            String[] params = bothEndCut(group).split(BR + BL);
            String path = getPath(values[1]);

            
        }
    }

    private String getPath(String path) {
        String p = path.replace(ACCESS, SEPARATOR_FILE);
        String pat = Setting.path + (p.startsWith(SEPARATOR_FILE) ? p : SEPARATOR_FILE + p);
        if (new File(pat).isFile()) return pat;
        else throw FileException.pathNoHaveError();
    }
}
