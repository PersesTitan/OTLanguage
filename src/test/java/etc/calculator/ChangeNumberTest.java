package etc.calculator;

import java.util.Stack;
import java.util.StringTokenizer;

public class ChangeNumberTest {
    public static void main(String[] args) {
        String a = "((1ㅇ+ㅇ3)ㅇ+ㅇ(3))";
        int s;
        StringBuilder builder = new StringBuilder(a);
        while ((s = builder.lastIndexOf("(")) != -1){
            int e = builder.indexOf(")", s) + 1;

            builder.replace(s, e, cal(builder.substring(s+1, e-1)));
        }
    }

    private static Stack<String> stack = new Stack<>();
    private static String cal(String value) {
        stack.clear();
        StringTokenizer tokenizer = new StringTokenizer(value, "ㅇ", true);
        while (tokenizer.hasMoreTokens()) {
            String t = tokenizer.nextToken();
            stack.add(0, t);
        }
        System.out.println(stack);
        return "test";
    }
}
