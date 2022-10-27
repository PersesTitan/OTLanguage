package cos.poison.root;

import bin.apply.Repository;
import bin.apply.sys.item.HpMap;
import bin.exception.VariableException;
import bin.token.LoopToken;
import work.StartWork;

import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableHTML implements LoopToken, StartWork {
    private final Map<String, Object> map = new HpMap(MODEL);
    private final Pattern pattern;

    public VariableHTML(String type) {
        this.pattern = Pattern.compile(START + type + BLANKS + VARIABLE_NAME + VARIABLE_PUT);
    }

    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line, String origen,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String[] variables
                = matchSplitError(matchSplitError(line, BLANKS, 2)[1], VARIABLE_PUT, 2);
        if (variables[0].startsWith("["))
            variables[0] = variables[0].replaceFirst(START + BL + "\\d+" + BR, "");
        if (Repository.noUse.contains(variables[0])) throw VariableException.reservedWorks();
        else if (map.containsKey(variables[0])) throw VariableException.sameVariable();
        map.put(variables[0], variables[1]);
    }

    @Override
    public void first() {

    }

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
}
