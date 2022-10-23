import bin.apply.sys.make.ChangeHangle;
import bin.token.LoopToken;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

public class MainTest implements LoopToken, ChangeHangle {

    public static void main(String[] args) throws AWTException, IOException {
//        Taskbar.getTaskbar().setIconImage(Toolkit.getDefaultToolkit().getImage(sb.toString()));
        Taskbar.getTaskbar().setIconImage(Toolkit.getDefaultToolkit().getImage("image/icon.png"));
        Robot robot = new Robot();
        JFrame frame = new JFrame("test");

        String a = "a";
        String b = "a";
        System.out.println(Arrays.toString(a.split(" ")));
        System.out.println(Arrays.toString(b.split(" ")));
//        try {
//        } catch (PatternSyntaxException e) {
//            String text = e.getDescription();
//            String start = "Named capturing group <";
//            int a = text.indexOf(start) + start.length();
//            int b = text.lastIndexOf("> is already defined");
//            if (b == -1 || a-start.length() == -1) throw MatchException.patternMatchError(null);
//            throw MatchException.patternMatchError(text.substring(a, b));
//        }

        System.out.println(Arrays.toString("메소드명[]".substring(0, "메소드명[]".length() - 1).split(BL)));
        System.out.println(Arrays.toString("메소드명[ㅇㅈㅇ ㅁㄴㅇㄹ]".substring(0, "메소드명[ㅇㅈㅇ ㅁㄴㅇㄹ]".length() - 1).split(BL)));

        System.out.println("\u3000");
        new MainTest();
    }

    public MainTest() {
        System.out.println(change("asaㅁㄴㅇㄹㄴㄹ 앙나나 "));
        String params = orMerge(TOTAL_LIST) + BLANKS + VARIABLE_HTML;
        String patternText = startEndMerge(
                METHOD, BLANKS, VARIABLE_HTML,
                "(", "(", BL, params, BR, ")+", "|", BL, BR, ")",
                BLANKS, BRACE_STYLE,
                "(", BLANK, RETURN, ")?");

        String[] strings = {"ㅇㅅㅇ ㅁㄴㄴ", "ㅇㅈㅇ ㅁㄴㅇㄹ", "ㅇㅈㅇ ㅁㄴㅇㄹ"};
        System.out.println(Arrays.deepToString(getParams(strings)));
        System.out.println(getParams(strings).length);
        System.out.println(new String[0][0].length);

        Pattern pattern = Pattern.compile(patternText);
        System.out.println(Arrays.toString("ㅇㅅㅇ 매개변수][adf".split(BR + BL)));
        System.out.println(pattern.matcher("ㅁㅅㅁ 메소드명[] (test,1,10)").find());
        System.out.println(pattern.matcher("ㅁㅅㅁ 메소드명[ㅇㅈㅇ ㅁㄴㅇㄹ][ㅇㅈㅇ ㅁㄴㅇㄹ] (test,1,10)=> a").find());
        System.out.println(pattern.matcher("ㅁㅅㅁ 메소드명[ㅇㅈㅇ] (test,1,10)").find());
    }

    private String[][] getParams(String[] params) {
        int count = params.length;
        String[][] param = new String[count][2];
        for (int i = 0; i<count; i++) param[i] = matchSplitError(params[i], BLANKS, 2);
        return param;
    }
}
