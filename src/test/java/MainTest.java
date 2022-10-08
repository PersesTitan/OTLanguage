import bin.token.LoopToken;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bin.apply.sys.item.Separator.SEPARATOR_LINE;

public class MainTest implements LoopToken {
    public static void main(String[] args) {
//        Taskbar.getTaskbar().setIconImage(Toolkit.getDefaultToolkit().getImage("/Users/persestitan/Documents/GitHub/OTLanguage/OTLanguage.png"));
//        Robot robot = new Robot();
//        JFrame frame = new JFrame("test");
        new MainTest();
    }

    public MainTest() {
        String params = orMerge(TOTAL_LIST) + BLANKS + VARIABLE_HTML;
        String patternText = startEndMerge(
                METHOD, BLANKS, VARIABLE_HTML,
                "(", "(", BL, params, BR, ")+", "|", BL, BR, ")",
                BLANKS, BRACE_STYLE(),
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
