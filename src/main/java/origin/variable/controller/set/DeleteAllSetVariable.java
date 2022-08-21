package origin.variable.controller.set;

import event.Setting;
import origin.variable.define.generic.GenericDelete;
import origin.variable.define.generic.delete.DeleteSet;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteAllSetVariable implements DeleteSet, GenericDelete {
    private final String text;
    private final String patternText;
    private final Pattern pattern;

    public DeleteAllSetVariable(String patternText) {
        this.text = patternText; // -!
        this.patternText = "^\\s*"+ Setting.variableStyle + patternText;
        this.pattern = Pattern.compile(this.patternText);
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line, Map<String, Map<String, Object>> repository) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            // 변수명-! => 변수명
            String group = matcher.group().strip().replaceFirst(text+"$", "");
            getSet(group, repository).clear(); // SET 가져오기 , 제거
        }
    }
}
