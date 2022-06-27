package comparison;

import java.util.regex.Pattern;

public class Small {
    private static final String SPECIFIED = "ㅇ>ㅇ";
    private final String patternText = "[\\S+.-]\\s*ㅇ>ㅇ\\s*[\\S+.-]";
    private final Pattern pattern = Pattern.compile(patternText);

    public boolean check(String line) throws Exception {
        return pattern.matcher(line).find();
    }
}
