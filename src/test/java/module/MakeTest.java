package module;

import java.util.*;

public class MakeTest {
    public static void main(String[] args) {
        String total = """
                                
                ㅉㅂㅉ
                ㅉㅂㅉ~ㅅㅌㅅ {
                    리스트 << 5;
                    리스트.add(15);
                    블린3=false
                    System.out.println(리스트);
                    System.out.println("안녕");
                    if (true) {
                        System.out.println("안녕1");
                    }
                    else if (false) {
                        System.out.println("안녕2");
                    }
                }
                                
                                
                ㅆㅁㅆ :블린1_
                ㅆㅁㅆ :블린2_
                ㅆㅁㅆ :블린3_
                """;

        start(total, 1);
    }

    private static int start(String total, int position) {
        boolean strBool = true;
        Stack<Integer> stack = new Stack<>();
        for (int i = position; i < total.length(); i++) {
            char c = total.charAt(i);
            switch (c) {
                case '\"', '\'' -> strBool = !strBool;
                case '{' -> {
                    if (strBool) stack.add(i);
                }
                case '}' -> {
                    if (strBool) {
                        if (stack.isEmpty()) return i;
                        stack.pop();
                    }
                }
            }
        }
        return position;
    }

    private final static int START_VALUE = 44032;
    private final static int END_VALUE = 55204;
}
