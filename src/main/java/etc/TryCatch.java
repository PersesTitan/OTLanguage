package etc;

import item.Setting;
import item.work.Check;

import java.util.regex.Pattern;

public class TryCatch extends Setting implements Check {
    private static final String SPECIFIED = "ㅜㅅㅜ";
    private final String PATTERN = "(\\n|^)\\s*ㅜㅅㅜ\\s";
    private final Pattern pattern = Pattern.compile(PATTERN);

    @Override
    public boolean check(String line) throws Exception {
        return pattern.matcher(line).find();
    }

    public void start(String line) {
        int position = line.indexOf(SPECIFIED) + SPECIFIED.length();
        if (line.strip().startsWith(SPECIFIED +" ")) position += 1;
        line = line.substring(position);
        try {
            super.start(line);
        } catch (Exception ignored) {}
    }
}
