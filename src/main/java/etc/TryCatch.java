package etc;

import item.Check;

import java.util.regex.Pattern;

public class TryCatch implements Check {
    private final String SPECIFIED = "눈_눈";
    private final String PATTERN = "\\n\\s*눈_눈\\s|^\\s*s눈_눈\\s";
    private final Pattern pattern = Pattern.compile(PATTERN);

    @Override
    public boolean check(String line) throws Exception {
        return pattern.matcher(line).find();
    }

    public void start(String line) {

    }
}
