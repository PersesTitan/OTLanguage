package loop;

import item.Check;
import item.Setting;
import item.work.LoopWork;

import java.util.regex.Pattern;

public class If extends Setting implements Check, LoopWork {
    private static final String SPECIFIED = "?ㅅ?";
    private final String patternText = "\\n\\s*\\?ㅅ\\?\\s|^\\s*\\?ㅅ\\?\\s";
    private final Pattern pattern = Pattern.compile(patternText);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line) {
        //?ㅅ? 제거 작업

    }
}
