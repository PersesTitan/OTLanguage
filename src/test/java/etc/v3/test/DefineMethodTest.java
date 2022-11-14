package etc.v3.test;

import java.util.*;

import static bin.token.LoopToken.LOOP_TOKEN;
import static bin.token.LoopToken.METHOD;

public class DefineMethodTest {
    public static void main(String[] args) {
        String test1 = """
                1 ㅁㅅㅁ 메소드명[] {
                2     ㅅㅁㅅ ㅁㄴㅇㄹ
                3 }
                4 ㅁㅅㅁ 메소드명1[ㅇㅈㅇ 매개변수] {
                5     ㅅㅁㅅ ㅁㄴㅇㄹ
                6 }
                """;
        String test = """
                ㅁㅅㅁ 메소드명[] (test,2,3)
                ㅁㅅㅁ 메소드명1[ㅇㅈㅇ 매개변수] (test,5,6) => asdf
                """;

        LOOP_TOKEN.put("test", test1);
//        DefineMethod defineMethod = new DefineMethod(LoopToken.METHOD);
        Map<String, Map<String, Object>> map = new HashMap<>();
        map.put(METHOD, new HashMap<>());
        test.lines().forEach(v -> {
//            if (defineMethod.check(v)) defineMethod.start(v, v, new Map[]{map});
        });

    }
}
