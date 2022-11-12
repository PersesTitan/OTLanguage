package etc.calculator.token;

import bin.exception.MatchException;
import etc.calculator.bool.BoolV3Test;
import etc.calculator.bool.CompareV3Test;

import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

import static bin.token.cal.BoolToken.FALSE;
import static bin.token.cal.BoolToken.TRUE;

public interface BlankV3Test {
    default String start(String line) {
        StringBuilder builder = new StringBuilder(line.strip());
        if (line.contains("(")) {
            int start;
            while ((start = builder.lastIndexOf("(")) != -1) {
                int end = builder.indexOf(")", start) + 1;
                builder.replace(start, end, (builder.substring(start+1, end-1)));
            }
        }
        return builder.toString();
    }

    // line : 123 ㅇ+ㅇ 343
    private String calculator(String line) {
        return line;
    }

    BoolV3Test bool = new BoolV3Test();
    CompareV3Test compare = new CompareV3Test();
    NumberV3Test number = new NumberV3Test();

    // boolean 반환
    // line : ㅇㅇ ㄸ ㄴㄴ
    default boolean getBool(String line, LinkedList<Map<String, Map<String, Object>>> ra) {
        StringBuilder builder = new StringBuilder(line.strip());
        if (line.contains("(")) {
            int start;
            while ((start = builder.lastIndexOf("(")) != -1) {
                int end = builder.indexOf(")", start) + 1;
                builder.replace(start, end, bool.start(compare.start(number.start(builder.substring(start+1, end-1), ra), ra), ra) ? TRUE : FALSE);
            }
        }
        return bool.start(compare.start(number.start(builder.toString(), ra), ra), ra);
    }

    // 숫자 계산
    // line : 123 ㅇ+ㅇ 343
    default String getNumber(String line, LinkedList<Map<String, Map<String, Object>>> ra) {
        StringBuilder builder = new StringBuilder(line.strip());
        if (line.contains("(")) {
            int start;
            while ((start = builder.lastIndexOf("(")) != -1) {
                int end = builder.indexOf(")", start) + 1;
                Stack<String> stack = number.start(builder.substring(start+1, end-1), ra);
                if (stack.size() != 1) throw new MatchException().grammarTypeError();
                builder.replace(start, end, stack.pop());
            }
        }
        Stack<String> stack = number.start(builder.toString(), ra);
        if (stack.size() != 1) throw new MatchException().grammarTypeError();
        return stack.pop();
    }
}
