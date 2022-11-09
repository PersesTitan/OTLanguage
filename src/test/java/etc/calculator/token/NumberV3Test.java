package etc.calculator.token;

import bin.exception.MatchException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public class NumberV3Test implements CalToken {
    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        stack.addAll(Arrays.asList("1", "+", "2", ">", "15", "%", "2", "*", "23", "+", "16"));
        new Number().start(stack);
    }

    public void start(Stack<String> stack, LinkedList<Map<String, Map<String, Object>>> ra) {
        int start, i;
        double total = 0;
        while (containsFirst(stack)) {
            boolean doubleCheck = true;
            for (start=getFirst(stack), i = start; (i < stack.size() && checkFirst(stack.get(i))); i+=2) {
                if (start == i) {
                    String a = stack.get(i-1);
                    if (doubleCheck && a.contains(".")) doubleCheck = false;
                    total = getDouble(a, ra);
                }
                String a = stack.get(i+1);
                if (doubleCheck && a.contains(".")) doubleCheck = false;
                total = cal(total, getDouble(a, ra), stack.get(i));
            }
            deleteStack(start, i, stack);
            stack.set(start-1, doubleCheck ? String.valueOf((long) total) : String.valueOf(total));
        }

        while (containsSecond(stack)) {
            boolean doubleCheck = true;
            for (start=getSecond(stack), i = start; (i < stack.size() && checkSecond(stack.get(i))); i+=2) {
                if (start == i) {
                    String a = stack.get(i-1);
                    if (doubleCheck && a.contains(".")) doubleCheck = false;
                    total = getDouble(stack.get(i-1), ra);
                }
                String a = stack.get(i+1);
                if (doubleCheck && a.contains(".")) doubleCheck = false;
                total = cal(total, getDouble(a, ra), stack.get(i));
            }
            deleteStack(start, i, stack);
            stack.set(start-1, doubleCheck ? String.valueOf((long) total) : String.valueOf(total));
        }

        System.out.println(stack);
    }

    private void deleteStack(int start, int end, Stack<String> stack) {
        for (int v = start; v < end; v+=2) {
            stack.remove(start);
            stack.remove(start-1);
        }
    }

    private double cal(double v1, double v2, String sing) {
        return switch (sing) {
            case "+" -> v1 + v2;
            case "-" -> v1 - v2;
            case "*" -> v1 * v2;
            case "/" -> v1 / v2;
            case "%" -> v1 % v2;
            default -> throw new MatchException().grammarTypeError();
        };
    }

    private int getFirst(Stack<String> stack) {
        int value = stack.indexOf("%");
        int a = stack.indexOf("*");
        if (a != -1) value = value == -1 ? a : Math.min(value, a);
        int b = stack.indexOf("/");
        if (b != -1) value = value == -1 ? a : Math.min(value, b);
        return value;
    }

    private int getSecond(Stack<String> stack) {
        int value = stack.indexOf("-");
        int a = stack.indexOf("+");
        if (a != -1) value = value == -1 ? a : Math.min(value, a);
        return value;
    }

    private boolean checkFirst(String sing) {
        return sing.equals("*") || sing.equals("%") || sing.equals("/");
    }

    private boolean checkSecond(String sing) {
        return sing.equals("+") || sing.equals("-");
    }

    private boolean containsFirst(Stack<String> stack) {
        return stack.contains("*") || stack.contains("/") || stack.contains("%");
    }

    private boolean containsSecond(Stack<String> stack) {
        return stack.contains("+") || stack.contains("-");
    }
}
