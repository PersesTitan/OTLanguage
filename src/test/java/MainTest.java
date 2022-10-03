import java.awt.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bin.apply.sys.item.Separator.SEPARATOR_LINE;

public class MainTest {
    public static void main(String[] args) {
        String line = "      ^ [1, 2, 3]   ^ (test,1,10)";
        System.out.println(Arrays.toString(line.split("\\^")));

    }
}
