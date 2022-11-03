package etc.regular;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpeedTest {
    public static void main(String[] args) {
//        Pattern pattern1 = Pattern.compile("[a-zA-Z0-9\uAC00-\uD787\u3131-\u314E\u314F-\u3163\u3041-\u3094\u30A1-\u30F4\u30FC\u3005\u3006\u3024\u4E00-\u9FA5]+");
        Pattern pattern1 = Pattern.compile("[a-zA-Z0-9\uAC00-\uD787\u3131-\u314E\u314F-\u3163]+");
        Pattern pattern2 = Pattern.compile("[a-zA-Z0-9 가-힇ㄱ-ㅎㅏ-ㅣ]+");
//        Matcher matcher1 = pattern1.matcher("");
//        Matcher matcher2 = pattern2.matcher("");

        String test = "asdfsaf안녕sfdsfa";



        int count = 1000000;

        long s1 = System.currentTimeMillis();
        for (int i = 0; i<count; i++) {
            Matcher matcher1 = pattern1.matcher(test);
            matcher1.find();
        }
        System.out.println(System.currentTimeMillis() - s1);

        long s = System.currentTimeMillis();
        for (int i = 0; i<count; i++) {
            Matcher matcher2 = pattern2.matcher(test);
            matcher2.find();
        }
        System.out.println(System.currentTimeMillis() - s);

    }
}
