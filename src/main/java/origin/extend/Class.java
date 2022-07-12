package origin.extend;

import java.util.regex.Pattern;

public class Class implements ClassWork {
    private final Pattern pattern = Pattern.compile("^\\s*ㅋㅅㅋ \\S");

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line) {

    }
}
