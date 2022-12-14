package module;

import javax.validation.Valid;
import java.util.*;

public class MakeTest {
    public static void main(String[] args) {
        List<Class<?>> list = new ArrayList<>();
        list.add(String.class);
        list.add(List.class);
        System.out.println(list);
    }

    private static void check(Class<?> klass, Object ob) {
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
