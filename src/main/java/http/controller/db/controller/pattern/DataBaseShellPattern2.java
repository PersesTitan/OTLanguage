package http.controller.db.controller.pattern;

import http.controller.db.define.DataBaseWork;
import http.controller.db.define.SelectWork;
import origin.exception.MatchException;
import origin.exception.MatchMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class DataBaseShellPattern2 implements SelectWork {
    private final int count;
    private final String patternText;
    private final String className;
    private final Pattern pattern;

    //[클래스명][메소드명]인자갯수...
    public DataBaseShellPattern2(String className, String methodName, int count) {
        this.className = className;
        this.count = count;
        this.patternText = className + "::" + methodName + "\\[[^\\[\\]]+]".repeat(Math.max(0, count));
        pattern = Pattern.compile(patternText);
    }

    public String[] getValues(Matcher matcher) {
        if (matcher.find()) {
            String group = matcher.group().trim();
            String[] values = group
                    .substring(className.length()+3, group.length()-1)
                    .split("]\\[");
            if (values.length != count) throw new MatchException(MatchMessage.grammarError);
            else return values;
        }
        throw new MatchException(MatchMessage.grammarError);
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public abstract String start(String line);

    public Pattern getPattern() {
        return pattern;
    }

    public String getPatternText() {
        return patternText;
    }
}
