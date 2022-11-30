package cos.poison.root;

import bin.apply.sys.item.HpMap;
import bin.exception.VariableException;
import bin.token.LoopToken;
import work.v3.StartWorkV3;

import java.awt.*;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableHTML extends StartWorkV3 implements LoopToken {
    private VariableHTML(int... counts) {
        super(counts);
    }
    private static VariableHTML variableHTML;
    public static VariableHTML getInstance() {
        if (variableHTML == null) {
            synchronized (VariableHTML.class) {
                variableHTML = new VariableHTML(1);
            }
        }
        return variableHTML;
    }

    public final Map<String, Object> map = new HpMap(MODEL);
    VariableHTML reset() {
        map.clear();
        return this;
    }

    // {{ 테스트 }}
    private final String left = ML.repeat(2), right = MR.repeat(2);
    private final String left_ = ML_.repeat(2), right_ = ML_.repeat(2);
    private final String patternText = left + BLANK + VARIABLE_GET_S + VARIABLE_HTML + BLANK + right;
    private final Matcher matcher = Pattern.compile(patternText).matcher("");
    // {{? 테스트 ?}}
    private final String leftIf = ML.repeat(2).concat(QUESTION), rightIf = QUESTION + MR.repeat(2);
    private final String leftIf_ = ML_.repeat(2).concat(QUESTION_S), rightIf_ = QUESTION_S + MR_.repeat(2);
    private final String patternTextIf = leftIf + BLANK + VARIABLE_GET_S + VARIABLE_HTML + BLANK + rightIf;
    private final Matcher matcherIf = Pattern.compile(patternTextIf).matcher("");
    public String replace(String htmlTotal) {
        if (htmlTotal.contains(left_) && htmlTotal.contains(right_)) {
            matcher.reset(htmlTotal);
            while (matcher.find()) {
                String group = matcher.group();
                String variableName = bothEndCut(group, left_.length(), right_.length())
                        .strip()
                        .substring(1);
                htmlTotal = htmlTotal.replace(group,
                        map.getOrDefault(variableName, group).toString());
            }
        }
        if (htmlTotal.contains(leftIf_) && htmlTotal.contains(rightIf_)) {
            matcherIf.reset(htmlTotal);
            while (matcherIf.find()) {
                String group = matcherIf.group();
                String variableName = bothEndCut(group, leftIf_.length(), rightIf_.length())
                        .strip()
                        .substring(1);
                htmlTotal = htmlTotal.replace(group,
                        map.getOrDefault(variableName, "").toString());
            }
        }

        return IfHTML.getInstance().check(htmlTotal)
                ? IfHTML.getInstance().replace(htmlTotal)
                : htmlTotal;
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String[] values = matchSplitError(params[0], VARIABLE_PUT, 2);
        if (map.containsKey(values[0])) throw new VariableException().sameVariable();
        map.put(values[0], values[1]);
    }
}
