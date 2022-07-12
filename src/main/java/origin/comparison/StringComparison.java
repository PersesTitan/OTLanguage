package origin.comparison;

import origin.item.work.ComparisonWork;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringComparison implements ComparisonWork {
    private final String TextPattern = "\\(\\D+ㅇ=ㅇ\\D+\\)";
    private final Pattern pattern = Pattern.compile(TextPattern);
    private final Pattern textPattern = Pattern.compile("\\D+");

    @Override
    public String start(String line) throws Exception {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String value = matcher.group();
            if (textPattern.matcher(value).find()) {
                Matcher textMatcher = pattern.matcher(value);
                String text1 = textMatcher.group(0);
                String text2 = textMatcher.group(1);
                line = line.replaceFirst(value, text1.equals(text2) ? "ㅇㅇ" : "ㄴㄴ");
            }
        }
        return line;
    }

    @Override
    public boolean check(String line) throws Exception {
        return pattern.matcher(line).find();
    }
}
