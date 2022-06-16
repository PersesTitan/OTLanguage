import item.work.LoopWork;
import loop.Bracket;
import org.junit.jupiter.api.Test;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoopWorkTest implements LoopWork {



    public static void main(String[] args) throws Exception {
        String testTotal = "dd";

        Bracket bracket = new Bracket();
        String pr = bracket.bracket(testTotal);

        System.out.println(pr);

        String[] str = pr.split("\n");
        for (String s : str) {
            System.out.println(s);
            System.out.println(bracket.check(s));
        }


    }

    @Override
    public boolean check(String total) {
        return false;
    }
}
