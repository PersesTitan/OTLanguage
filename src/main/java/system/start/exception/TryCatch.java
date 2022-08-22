package system.start.exception;

import event.Setting;
import origin.variable.model.Repository;
import system.work.ThreadWork;

import java.util.regex.Pattern;

public class TryCatch implements ThreadWork {
    private final String patternText;
    private final Pattern pattern;

    public TryCatch(String patternText) {
        this.patternText = "^\\s*" + patternText + "\\s*";
        this.pattern = Pattern.compile(this.patternText);
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line) {
        line = line.replaceFirst(patternText, "");
        if (line.isBlank()) return;
        if (Repository.uuidMap.containsKey(line.trim())) {
            String total = Repository.uuidMap.get(line.trim());
            try {
                for (String lines : total.split("\\n")) Setting.start(lines);
            } catch (Exception ignored) {}
        } else {
            try {
                Setting.start(line);
            } catch (Exception ignored) {}
        }
    }
}
