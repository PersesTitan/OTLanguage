import item.work.LoopWork;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;

public class LoopWorkTest implements LoopWork {

    public static void main(String[] args) {
        String testTotal = "?ㅅ? \n ?ㅅ? \n ^^ \n^^";
        Matcher matcher = pattern.matcher(testTotal);
        while (matcher.find()) {
            String str = String.format("%s %d %d", matcher.group(), matcher.start(), matcher.end());
            System.out.println(str);
        }
    }

    @Override
    public boolean check(String total) {
        return false;
    }
}
