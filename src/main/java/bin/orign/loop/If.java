package bin.orign.loop;

import bin.apply.sys.make.StartLine;
import bin.exception.MatchException;
import bin.token.LoopToken;
import bin.token.cal.BoolToken;
import work.StartWork;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class If implements LoopToken, StartWork, BoolToken {
    private final String type1;
    private final String type2;
    private final String type3;
    private Pattern IF_Pattern;
    private Pattern IF_ELSE_Pattern;
    private Pattern ELSE_Pattern;
    private Pattern pattern;

    public If(String type1, String type2, String type3) {
        this.type1 = type1;
        this.type2 = type2;
        this.type3 = type3;
        String BOOLS = orMerge(TRUE, FALSE);
        String IF_PatternText = blacksMerge(type1, BOOLS, BRACE_STYLE());
        String IF_ELSE_PatternText = blackMerge("(", blacksMerge(type2, BOOLS, BRACE_STYLE()), ")*");
        String ELSE_PatternText = blackMerge("(", blacksMerge(type3, BRACE_STYLE()), ")?");
        this.IF_Pattern = Pattern.compile(IF_PatternText);
        this.IF_ELSE_Pattern = Pattern.compile(blackMerge("(", blacksMerge(type2, BOOLS, BRACE_STYLE()), ")"));
        this.ELSE_Pattern = Pattern.compile(blackMerge("(", blacksMerge(type3, BRACE_STYLE()), ")"));
        this.pattern = Pattern.compile(startEndMerge(IF_PatternText, IF_ELSE_PatternText, ELSE_PatternText));
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String group = matcher.group().strip();
            startLine(group, repositoryArray);
        }
    }

    private void startLine(String line, Map<String, Map<String, Object>>[] repository) {
        Matcher ifPattern = IF_Pattern.matcher(line);
        if (ifPattern.find()) {
            String[] tokens = ifPattern.group().strip().split("\\s+", 3);
            if (tokens.length != 3) throw MatchException.grammarError();
            boolean bool = tokens[1].equals("ㅇㅇ");
            String token = bothEndCut(tokens[2]);
            if (bool) startValue(token, repository);
            else {
                if (!elseIF(line, repository)) ELSE(line, repository);
            }
        }
    }

    private boolean elseIF(String line, Map<String, Map<String, Object>>[] repository) {
        Matcher matcher = IF_ELSE_Pattern.matcher(line);
        while (matcher.find()) {
            String[] tokens = matcher.group().strip().split("\\s+", 3);
            if (tokens.length != 3) throw MatchException.grammarError();
            boolean bool = tokens[1].equals("ㅇㅇ");
            String token = bothEndCut(tokens[2]);
            if (bool) {
                startValue(token, repository);
                return true;
            }
        }
        return false;
    }

    private void ELSE(String line, Map<String, Map<String, Object>>[] repository) {
        Matcher matcher = ELSE_Pattern.matcher(line);
        if (matcher.find()) {
            String[] tokens = matcher.group().strip().split("\\s+", 2);
            if (tokens.length != 2) throw MatchException.grammarError();
            String token = bothEndCut(tokens[1]);
            startValue(token, repository);
        }
    }

    // FileName, StartPos, EndPos
    private void startValue(String line, Map<String, Map<String, Object>>[] repository) {
        String[] values = line.split(COMMA, 3);
        if (values.length != 3) throw MatchException.grammarError();
        else if (!LOOP_TOKEN.containsKey(values[0])) throw MatchException.grammarError();
        int start = Integer.parseInt(values[1]);
        int end = Integer.parseInt(values[2]);
        String total = LOOP_TOKEN.get(values[0]).substring(start, end);
        StartLine.startLine(total, values[0], repository);
    }
}
