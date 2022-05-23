package comparison;

import java.util.regex.Pattern;

public class Small {
    private static final String SPECIFIED = "ㅇ>ㅇ";
    private final String patternText = "\\s*ㅇ>ㅇ\\s";
    private final Pattern pattern = Pattern.compile(patternText);
}
