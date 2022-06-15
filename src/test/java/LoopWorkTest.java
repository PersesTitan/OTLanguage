import item.work.LoopWork;
import loop.Bracket;
import org.junit.jupiter.api.Test;

import java.util.StringTokenizer;
import java.util.regex.Matcher;

public class LoopWorkTest implements LoopWork {

    public static void main(String[] args) throws Exception {
        String testTotal = "adsaf\n" +
                "{asfs{afsfs\n" +
                "}\n" +
                "asfs{sf{f\n" +
                "  saf\n" +
                "}af}af}a\n" +
                "fs{af}s\n" +
                "" +
                "" +
                "f{sfsf}af\n";
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
