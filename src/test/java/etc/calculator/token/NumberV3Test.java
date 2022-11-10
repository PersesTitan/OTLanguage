package etc.calculator.token;

import bin.apply.Setting;
import bin.exception.MatchException;
import etc.calculator.bool.BoolV3Test;
import etc.calculator.bool.CompareV3Test;

import java.util.*;

import static bin.apply.Repository.repository;
import static bin.token.Token.NO_TOKEN;
import static bin.token.Token.TOKEN;
import static bin.token.VariableToken.INT_VARIABLE;
import static bin.token.VariableToken.LIST_INTEGER;
import static bin.token.cal.BoolToken.*;

public class NumberV3Test implements CalToken {
    public static void main(String[] args) {
        NumberV3Test n = new NumberV3Test();
        BoolV3Test b = new BoolV3Test();
        CompareV3Test c = new CompareV3Test();

        new Setting();

        repository.get(0).get(INT_VARIABLE).put("변수", 111);
        repository.get(0).get(LIST_INTEGER).put("리스트", "<<[1, 2, 3]");

        String line = "10 ㅇ+ㅇ 변수 ㅇ+ㅇ 리스트>>6";
        System.out.println(n.start(line, repository));
    }

    private final Stack<String> stack = new Stack<>();
    public Stack<String> start(String line, LinkedList<Map<String, Map<String, Object>>> ra) {
        stack.clear();
        StringTokenizer tokenizer = new StringTokenizer(line, TOKEN + OR + NO_TOKEN + AND, true);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (token.isBlank()) continue;
            if (token.equals(TOKEN)) {
                String a = tokenizer.nextToken();
                if (a.equals(TOKEN)) stack.add(TRUE);
                else if (a.equals(NO_TOKEN)) stack.add(NOT);
                else {
                    if (tokenizer.nextToken().equals(TOKEN)) stack.add(a);
                    else throw new MatchException().grammarTypeError();
                }
            } else if (token.equals(NO_TOKEN)) {
                if (tokenizer.nextToken().equals(NO_TOKEN)) stack.add(FALSE);
                else throw new MatchException().grammarTypeError();
            } else stack.add(token.strip());
        }

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
            stack.set(start-1, doubleCheck ? checkInteger(total) : String.valueOf(total));
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
            stack.set(start-1, doubleCheck ? checkInteger(total) : String.valueOf(total));
        }
        return stack;
    }

    private String checkInteger(double d) {
        return (d == Math.floor(d) && !Double.isInfinite(d)) ? Long.toString((long) d) : Double.toString(d);
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
        if (b != -1) value = value == -1 ? b : Math.min(value, b);
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
