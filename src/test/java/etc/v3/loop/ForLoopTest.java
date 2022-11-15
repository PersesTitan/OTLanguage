package etc.v3.loop;

import bin.calculator.tool.Calculator;
import bin.check.VariableType;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.MergeToken;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import static bin.token.LoopToken.PUTIN_TOKEN;
import static bin.token.VariableToken.ORIGIN_LIST;

public class ForLoopTest implements MergeToken, Calculator {
    public static void main(String[] args) {
        new ForLoopTest().start("10^1^1 (test,1,10)      <=     ㅇㅈㅇ ㅁㅁ", null);
    }

    public boolean check(String line) {
        return line.chars().filter(v -> v == '^').count() == 2;
    }

    public void start(String line, LinkedList<Map<String, Map<String, Object>>> ra) {
        try {
            startTokens(line, new StringTokenizer(line, "^"), ra);
        } catch (NoSuchElementException e) {
            throw new MatchException().grammarError();
        } catch (ClassCastException | NumberFormatException e) {
            throw new VariableException().typeMatch();
        }
    }

    private void startTokens(String line, StringTokenizer tokenizer,
                             LinkedList<Map<String, Map<String, Object>>> ra) {
        int position = line.lastIndexOf(PUTIN_TOKEN);
        if (position != -1) {
            // ㅇㅈㅇ ㅁㅁ
            StringTokenizer variable = new StringTokenizer(line.substring(position + PUTIN_TOKEN.length()).strip());
            String variableType = variable.nextToken(); // ㅇㅈㅇ
            String variableName = variable.nextToken(); // ㅁㅁ

            variableDefineError(variableName, ra.get(0));
            if (!ORIGIN_LIST.contains(variableType)) throw new VariableException().typeMatch();

        }
    }


}
