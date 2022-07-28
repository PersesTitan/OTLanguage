package http.controller.db.controller.pattern;

import event.db.DBSetting;
import origin.exception.MatchException;
import origin.exception.MatchMessage;
import origin.loop.model.LoopWork;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//클래스 정의
public abstract class DataBaseShell implements LoopWork {
    private final int count;
    private final String className;
    private final String patternText;
    private final Pattern pattern;

    public DataBaseShell(String className, int count) {
        this.count = count;
        this.className = className;
        patternText = "^\\s*" + className + "\\[[^\\[\\]]+]".repeat(Math.max(0, count));
        pattern = Pattern.compile(patternText);
    }

    @Override
    public boolean check(String line) {
        if (DBSetting.databaseSetting != null) return false;
        return pattern.matcher(line).find();
    }

    public String[] getValues(Matcher matcher) {
        if (matcher.find()) {
            String group = matcher.group().trim();
            String[] values = group
                    .substring(className.length()+1, group.length()-1)
                    .split("]\\[");
            if (values.length != count) throw new MatchException(MatchMessage.grammarError);
            else return values;
        }
        throw new MatchException(MatchMessage.grammarError);
    }

    @Override
    public abstract void start(String line);

    @Override
    public String getPattern() {
        return patternText;
    }
}
