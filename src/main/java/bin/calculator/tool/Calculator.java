package bin.calculator.tool;

import bin.calculator.bool.BoolCalculator;
import bin.calculator.bool.CompareCalculator;
import bin.calculator.number.NumberCalculator;
import bin.exception.MatchException;

import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

import static bin.calculator.tool.CalculatorTool.compare;
import static bin.calculator.tool.CalculatorTool.number;
import static bin.check.VariableCheck.isDouble;
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
        try {
            line = line.strip();
            if (line.equals(TRUE) || line.equals(FALSE)) return line.equals(TRUE);

            Stack<String> stack = n.getStack(line);
            StringBuilder builder = new StringBuilder(line);
            if (line.contains("(")) {
                int start;
                while ((start = builder.lastIndexOf("(")) != -1) {
                    int end = builder.indexOf(")", start) + 1;
                    if (stack.stream().anyMatch(number::containsKey)) n.start(stack, ra);
                    if (stack.stream().anyMatch(compare::containsKey)) c.start(stack, ra);
                    builder.replace(start, end, b.start(stack, ra) ? TRUE : FALSE);
                }
            }
            if (stack.stream().anyMatch(number::containsKey)) n.start(stack, ra);
            if (stack.stream().anyMatch(compare::containsKey)) c.start(stack, ra);
            return b.start(stack, ra);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new MatchException().grammarError();
        }
    }

    // 숫자 계산
    // line : 123 ㅇ+ㅇ 343
    default String getNumberStr(String line, LinkedList<Map<String, Map<String, Object>>> ra) {
        try {
            StringBuilder builder = new StringBuilder(line.strip());
            if (line.contains("(")) {
                int start;
                while ((start = builder.lastIndexOf("(")) != -1) {
                    int end = builder.indexOf(")", start) + 1;
                    Stack<String> stack = n.getStack(builder.substring(start+1, end-1));
                    n.start(stack, ra);
                    if (stack.size() != 1) throw new MatchException().grammarTypeError(stack.toString(), VALID);
                    builder.replace(start, end, stack.pop());
                }
            }
            Stack<String> stack = n.getStack(builder.toString());
            n.start(stack, ra);
            if (stack.size() != 1) throw new MatchException().grammarTypeError(stack.toString(), VALID);
            return stack.pop();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new MatchException().grammarError();
        }
    }

    default double getNumber(String line, LinkedList<Map<String, Map<String, Object>>> ra) {
        return Double.parseDouble(isDouble(line) ? line : getNumberStr(line, ra));
    }
}
