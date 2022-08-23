package origin.variable.json.controller.json.set;

import event.token.Token;
import org.json.JSONObject;
import origin.exception.MatchException;
import origin.exception.MatchMessage;
import origin.variable.json.controller.json.define.JsonRepository;
import origin.variable.json.controller.json.define.MakeJsonWork;
import tool.Tools;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static event.token.Token.*;

public class SetJsonObject implements Token, MakeJsonWork, JsonRepository {
    private final String patternText;
    private final Pattern pattern;

    public SetJsonObject(String patternText) {
        this.patternText = patternText;
        this.pattern = Pattern.compile(
                makeBlank(START, VARIABLE_NAME, patternText, STRING_PATTERN, patternText, ALL));
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

            // STRING_PATTERN, patternText, ALL
            String value1 = group
                    .replaceFirst(makeBlank(START, VARIABLE_NAME, patternText), "")
                    .trim();
            String value2; // STRING_PATTERN
            Matcher m = Pattern.compile(STRING_PATTERN).matcher(value1);
            if (m.find()) value2 = m.group();
            else throw new MatchException(MatchMessage.grammarError);
            String value3 = value1
                    .replaceFirst("^\\s*" + STRING_PATTERN + "\\s*" + this.patternText + "\\s*", "")
                    .trim();

            JSONObject object = getObject(variableName);
            if (!checkMap(object, value3)) {
                if (object == null) {
                    object = new JSONObject();
                    jsonObjectRepository.put(variableName, object);
                }
                object.put(Tools.deleteBothEnds(value2), changeValue(value3));
            }
        }
    }

    private boolean checkMap(JSONObject jsonObject, String line) {
        if (line.startsWith(ML) && line.endsWith(MR)) {
            line = Tools.deleteBothEnds(line);
            String[] values = Tools.splitArray(line); // key=value
            for (String value : values) {
                StringTokenizer tokenizer = new StringTokenizer(value, "=");
                StringBuilder builder = new StringBuilder();
                String k = tokenizer.nextToken();
                while (tokenizer.hasMoreTokens())
                    builder.append(tokenizer).append("=");
                builder.deleteCharAt(builder.length()-1);
                String v = builder.toString();
                jsonObject.put(k, v);
            }
            return true;
        }
        return false;
    }
}
