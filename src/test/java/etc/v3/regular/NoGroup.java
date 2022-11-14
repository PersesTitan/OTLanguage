package etc.v3.regular;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoGroup {
    public static void main(String[] args) {
        Matcher matcher = Pattern.compile("a(?=b)").matcher("ab");
        if (matcher.find()) System.out.println(matcher.group());
        matcher = Pattern.compile("(?<=a)b").matcher("ab");
        if (matcher.find()) System.out.println(matcher.group());
    }
}
