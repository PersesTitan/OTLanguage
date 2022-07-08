import origin.item.Setting;
import origin.item.work.LoopWork;
import origin.loop.Bracket;

public class LoopWorkTest extends Setting implements LoopWork {

    public static void main(String[] args) throws Exception {
        String testTotal = "?ㅅ?\n\n\n{ }\n{  }\n\n {   }\n{  } 100^10^-1{         {}}";

        Bracket bracket = new Bracket();
        String pr = bracket.bracket(testTotal);

//        System.out.println(pr);

        String[] str = pr.split("\n");
        for (String s : str) {
            System.out.println(s);
            System.out.println(forP.check(s));
            if (forP.check(s)) {
                forP.start(s);
            }
//            System.out.println(bracket.check(s));
        }
    }

    @Override
    public boolean check(String total) {
        return false;
    }
}
