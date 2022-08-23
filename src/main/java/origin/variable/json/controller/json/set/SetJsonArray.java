package origin.variable.json.controller.json.set;

import event.token.Token;
import org.json.JSONArray;
import origin.variable.json.controller.json.define.JsonRepository;
import origin.variable.json.controller.json.define.MakeJsonWork;
import tool.Tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static event.token.Token.makeBlank;

public class SetJsonArray implements
        Token, MakeJsonWork, JsonRepository {
    private final String patternText;
    private final Pattern pattern;

    public SetJsonArray(String patternText) {
        this.patternText = patternText;
        this.pattern = Pattern.compile(
                makeBlank(START, VARIABLE_NAME, patternText, ALL));
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String group = matcher.group().strip();
            String[] values = group.split(this.patternText);
            String variableName = values[0].trim();
            String value = group.replaceFirst(
                    makeBlank(START, VARIABLE_NAME, patternText), "")
                    .trim();
            JSONArray array = getArray(variableName);
            if (!checkArray(array, value)) {
                if (array == null) {
                    array = new JSONArray();
                    jsonArrayRepository.put(variableName, array);
                }
                array.put(changeValue(value));
            }
        }
    }

    // line : [값1, 값2, ...]
    private boolean checkArray(JSONArray array, String line) {
        if (line.startsWith(BL) && line.endsWith(BR)) {
            line = Tools.deleteBothEnds(line);
            String[] values = Tools.splitArray(line);
            for (String value : values) array.put(changeValue(value));
            return true;
        } return false;
    }
}
