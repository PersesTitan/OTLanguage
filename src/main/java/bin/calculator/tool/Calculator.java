package bin.calculator.tool;

import bin.calculator.bool.BoolCalculator;
import bin.calculator.bool.CompareCalculator;
import bin.calculator.number.NumberCalculator;
import bin.exception.MatchException;

import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

import static bin.exception.MatchException.GrammarTypeClass.VALID;
import static bin.token.cal.BoolToken.FALSE;
import static bin.token.cal.BoolToken.TRUE;

public interface Calculator {
    BoolCalculator b = new BoolCalculator();
    CompareCalculator c = new CompareCalculator();
    NumberCalculator n = new NumberCalculator();

    // boolean 반환
    // line : ㅇㅇ ㄸ ㄴㄴ
    static boolean getBool(String line, LinkedList<Map<String, Map<String, Object>>> ra) {
        StringBuilder builder = new StringBuilder(line.strip());
        if (line.contains("(")) {
            int start;
            while ((start = builder.lastIndexOf("(")) != -1) {
                int end = builder.indexOf(")", start) + 1;
                builder.replace(start, end, b.start(c.start(n.start(builder.substring(start+1, end-1), ra), ra), ra) ? TRUE : FALSE);
            }
        }
        return b.start(c.start(n.start(builder.toString(), ra), ra), ra);
    }

    // 숫자 계산
    // line : 123 ㅇ+ㅇ 343
    default String getNumberStr(String line, LinkedList<Map<String, Map<String, Object>>> ra) {
        StringBuilder builder = new StringBuilder(line.strip());
        if (line.contains("(")) {
            int start;
            while ((start = builder.lastIndexOf("(")) != -1) {
                int end = builder.indexOf(")", start) + 1;
                Stack<String> stack = n.start(builder.substring(start+1, end-1), ra);
                if (stack.size() != 1) throw new MatchException().grammarTypeError(stack.toString(), VALID);
                builder.replace(start, end, stack.pop());
            }
        }
        Stack<String> stack = n.start(builder.toString(), ra);
        if (stack.size() != 1) throw new MatchException().grammarTypeError(stack.toString(), VALID);
        return stack.pop();
    }

    default double getNumber(String line, LinkedList<Map<String, Map<String, Object>>> ra) {
        return Double.parseDouble(getNumberStr(line, ra));
    }
}
