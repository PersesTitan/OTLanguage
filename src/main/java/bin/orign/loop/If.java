package bin.orign.loop;

import bin.apply.sys.make.StartLine;
import bin.exception.MatchException;
import bin.token.LoopToken;
import bin.token.cal.BoolToken;
import work.StartWork;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class If implements LoopToken, StartWork, BoolToken {
    private final String type1;
    private final String type2;
    private final String type3;
    private final String typeToken1;
    private final String typeToken2;
    private final String typeToken3;

    private Pattern IF_Pattern;
    private Pattern IF_ELSE_Pattern;
    private Pattern ELSE_Pattern;
    private Pattern pattern;

    public If(String type1, String type2, String type3) {
        this.type1 = type1;
        this.type2 = type2;
        this.type3 = type3;
        this.typeToken1 = type1.replace("\\", "");
        this.typeToken2 = type2.replace("\\", "");
        this.typeToken3 = type3.replace("\\", "");
    }

    @Override
    public boolean check(String line) {
        makePattern(BRACE_STYLE());
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {
        startLine(line.strip(), repositoryArray);
    }

    private void startLine(String line, Map<String, Map<String, Object>>[] repository) {
        StringTokenizer tokenizer = new StringTokenizer(line);

        String token;
        if (tokenizer.hasMoreTokens() &&
            tokenizer.nextToken().equals(typeToken1) &&
            ((token = tokenizer.nextToken()).equals(TRUE) || token.equals(FALSE))) {
            if (token.equals(TRUE)) startValue(bothEndCut(tokenizer.nextToken()), repository);
            else {
                tokenizer.nextToken();
                while (tokenizer.hasMoreTokens()) {
                    token = tokenizer.nextToken();
                    if (token.equals(typeToken2) &&
                       ((token = tokenizer.nextToken()).equals(TRUE) || token.equals(FALSE))) {
                        if (token.equals(TRUE)) {
                            startValue(bothEndCut(tokenizer.nextToken()), repository);
                            break;
                        } else tokenizer.nextToken();
                    } else if (token.equals(typeToken3)) {
                        startValue(bothEndCut(tokenizer.nextToken()), repository);
                        break;
                    } else throw MatchException.grammarError();
                }
            }
        } else throw MatchException.grammarError();
    }

    // FileName, StartPos, EndPos
    private void startValue(String line, Map<String, Map<String, Object>>[] repository) {
        String[] values = matchSplitError(line, COMMA, 3);
        if (!LOOP_TOKEN.containsKey(values[0])) throw MatchException.grammarError();
        String total = LOOP_TOKEN.get(values[0]);
        int s = total.indexOf("\n" + values[1] + " ");
        int e = total.indexOf("\n" + values[2] + " ");
        StartLine.startLine(total.substring(s, e), values[0], repository);
    }

    // Pattern 만드는 로직
    private void makePattern(String BRACE_STYLE) {
        String IF_PatternText = START + BLANK + blacksMerge(type1, BOOL, BRACE_STYLE);
        String IF_ELSE_PatternText = blackMerge("(", blacksMerge(type2, BOOL, BRACE_STYLE), ")*");
        String ELSE_PatternText = blackMerge("(", blacksMerge(type3, BRACE_STYLE), ")?");
        this.IF_Pattern = Pattern.compile(IF_PatternText);
        this.IF_ELSE_Pattern = Pattern.compile(blackMerge("(", blacksMerge(type2, BOOL, BRACE_STYLE), ")"));
        this.ELSE_Pattern = Pattern.compile(blackMerge("(", blacksMerge(type3, BRACE_STYLE), ")"));
        this.pattern = Pattern.compile(startEndMerge(IF_PatternText, IF_ELSE_PatternText, ELSE_PatternText));
    }
}
