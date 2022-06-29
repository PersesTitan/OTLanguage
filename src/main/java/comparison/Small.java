package comparison;

import java.util.regex.Pattern;

public class Small {
    private static final String SPECIFIED = "ㅇ>ㅇ";
    private final String patternText = "-?\\d+\\.?\\d*\\s*ㅇ>ㅇ\\s*-?\\d+\\.?\\d*";
    private final Pattern pattern = Pattern.compile(patternText);

    public boolean check(String line) throws Exception {
        return pattern.matcher(line).find();
    }
}
