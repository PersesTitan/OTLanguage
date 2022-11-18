package cos.poison.root;

import bin.apply.sys.item.HpMap;
import bin.exception.VariableException;
import bin.token.LoopToken;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableHTML extends StartWorkV3 implements LoopToken {
    private final Map<String, Object> map = new HpMap(MODEL);

    public VariableHTML reset() {
        map.clear();
        return this;
    }

    // 번역기
    private final String patternText = ML.repeat(2) + BLANK + VARIABLE_GET_S + VARIABLE_HTML + BLANK + MR.repeat(2);
    private final Pattern p = Pattern.compile(patternText);
    public String replace(String htmlTotal) {
        Matcher matcher = p.matcher(htmlTotal);
        while (matcher.find()) {
            String group = matcher.group();
            String variableName = bothEndCut(group, 2, 2)
                    .strip()
                    .substring(1);
            if (map.containsKey(variableName))
                htmlTotal = htmlTotal.replaceAll(Pattern.quote(group), map.get(variableName).toString());
        }
        return htmlTotal;
    }

    public VariableHTML(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String[] values = matchSplitError(params[0], VARIABLE_PUT, 2);
        if (map.containsKey(values[0])) throw new VariableException().sameVariable();
        map.put(values[0], values[1]);
    }
}
