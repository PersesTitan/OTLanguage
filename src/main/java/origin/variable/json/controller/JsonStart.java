package origin.variable.json.controller;

import event.token.Token;
import origin.variable.json.define.MakeJsonWork;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static event.token.Token.makeBlank;

public class JsonStart implements MakeJsonWork, Token {
    private final String patternText;
    private final Pattern pattern;

    public JsonStart(String patternText) {
        this.patternText = "^\\s*" + patternText;
        this.pattern = Pattern.compile(this.patternText);
    }

    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    public void start(String line) {
    }

    // ( key )
    private void makeMap(String line) {
        // ( ... )
        String patternText = makeBlank(SL, "[\\s\\S]+", SR);
        Matcher matcher = Pattern.compile(patternText).matcher(line);
        if (matcher.find()) {
            String group = matcher.group();
            group = group.substring(1, group.length()-1).strip();

        }
    }
}
