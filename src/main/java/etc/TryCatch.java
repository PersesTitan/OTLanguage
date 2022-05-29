package etc;

import item.Check;
import item.Setting;

import java.util.regex.Pattern;

public class TryCatch extends Setting implements Check {
    private final String SPECIFIED = "ㅜㅅㅜ";
    private final String PATTERN = "\\n\\s*ㅜㅅㅜ\\s|^\\s*ㅜㅅㅜ\\s";
    private final Pattern pattern = Pattern.compile(PATTERN);

    @Override
    public boolean check(String line) throws Exception {
        return pattern.matcher(line).find();
    }

    public static void start(String line, boolean bool) {
        if (bool) {
            try {
            } catch (Exception ignored) {}
        } else {
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
