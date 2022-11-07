package etc.loop;

import java.util.Arrays;
import java.util.StringTokenizer;

public class IfTest {
    public static void main(String[] args) {

        String s = "[14]안녕하세요";
        System.out.println(s.substring(1, s.indexOf("]")));

        new IfTest("?ㅉ? (test,1,2)");
        new IfTest("?ㅈ? ㅇㅇ ㄸ ㄴㄴ (test,1,2) ?ㅈ? ㅇㅇㄲㄴㄴ:ㅁㅁ_ (test,1,2) ");
        new IfTest("?ㅈ? ㅇㅇ ㄸ ㄴㄴ (test,1,2) ?ㅈ? ㅇㅇㄲㄴㄴ:ㅁㅁ_ (test,1,2) ?ㅉ? (test,1,2)");
        new IfTest("?ㅈ? ㅇㅇ ㄸ ㄴㄴ (test,1,2) ?ㅈ? ㅇㅇㄲㄴㄴ:ㅁㅁ_ (test,1,2) ?ㅉ? af (test,1,2)");
    }

    private IfTest(String line) {
        String[] lines = line.split("\\?ㅈ\\?");
        System.out.println(Arrays.toString(lines));
    }
}
