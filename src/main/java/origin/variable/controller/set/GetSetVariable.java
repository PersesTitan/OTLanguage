package origin.variable.controller.set;

import event.Setting;
import origin.exception.IndexException;
import origin.exception.IndexMessage;
import origin.exception.MatchException;
import origin.exception.MatchMessage;
import origin.variable.define.generic.get.GetSet;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetSetVariable implements GetSet {
    //:변수명>key(_ )
    private final String text; // >>>
    private final String patternText;
    private final Pattern pattern;

    public GetSetVariable(String patternText) {
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
            group = group.substring(1, group.length()-1); // 변수명<key
            String[] keys = group.split(text);
            if (keys.length != 2) throw new MatchException(MatchMessage.grammarError);
            String key = keys[0]; //변수명
            int index = Integer.parseInt(keys[1].replaceAll("[^0-9]", "")); //키 값
            Object value = getValue(getSet(key).iterator(), index);
            line = line.replaceFirst(patternText, value.toString());
        }
        return line;
    }

    private Object getValue(Iterator<Object> iterator, int index) {
        int i = 0;
        while (iterator.hasNext()) {
            if (i == index) return iterator.next();
            iterator.next();
            i++;
        }
        throw new IndexException(IndexMessage.nullIndex);
    }
}
