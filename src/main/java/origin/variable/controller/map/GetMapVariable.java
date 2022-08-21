package origin.variable.controller.map;

import event.Setting;
import origin.exception.*;
import origin.variable.define.generic.get.GetMap;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetMapVariable implements GetMap {
    //:변수명>>>key(_ )
    private final String text; // >>>
    private final String patternText;
    private final Pattern pattern;

    public GetMapVariable(String patternText) {
        this.text = patternText;
        this.patternText = ":"+ Setting.variableStyle+patternText+"[\\s\\S]+[ _]";
        this.pattern = Pattern.compile(this.patternText);
    }

    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    public String start(String line) {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String group = matcher.group();
            group = group.substring(1, group.length()-1); // 변수명<<<key
            String[] keys = group.split(text);
            if (keys.length != 2) throw new MatchException(MatchMessage.grammarError);
            String key = keys[0]; //변수명
            String value = keys[1]; //키 값
            Map<String, Object> map = getMap(key);
            if (!map.containsKey(value)) throw new IndexException(IndexMessage.nullKey);
            line = line.replaceFirst(patternText, map.get(value).toString());
        }
        return line;
    }
}
