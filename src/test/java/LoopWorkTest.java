import item.work.LoopWork;
import loop.Bracket;

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
