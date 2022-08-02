package http.controller.db.controller.pattern;

import http.controller.db.DatabaseSetting;
import http.controller.db.define.DataBaseWork;
import http.controller.db.place.DataBasePlace;
import origin.exception.MatchException;
import origin.exception.MatchMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class DataBaseShellPattern1 implements DataBaseWork {
    private final String patternText;
    private final String className;
    private final int count;
    private final Pattern pattern;

    //[클래스명][메소드명]인자갯수...
    public DataBaseShellPattern1(String className, String methodName, int count) {
        DataBasePlace.methodName.add(methodName);
        this.className = className;
        this.count = count;
        this.patternText = "^\\s*" + className + "~" + methodName + "\\[[^\\[\\]]+]".repeat(Math.max(0, count)) + "\\s*$";
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

    public Pattern getPattern() {
        return pattern;
    }

    public String getPatternText() {
        return patternText;
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public abstract void start(String line);
}
