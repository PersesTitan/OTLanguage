package origin.variable.json.controller.json.controller;

import event.token.Token;
import org.json.JSONArray;
import origin.variable.json.controller.json.define.JsonRepository;
import origin.variable.json.controller.json.define.MakeJsonWork;

import java.util.regex.Pattern;

import static event.token.Token.make;
import static event.token.Token.makeBlank;

public class MakeJsonArray implements MakeJsonWork, Token {
    private final String patternText;
    private final Pattern pattern;

    public MakeJsonArray(String patternText) {
        this.patternText = makeBlank(START, patternText);
        this.pattern = Pattern.compile(
                makeBlank(START, patternText, make(BLANK, VARIABLE_NAME)));
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line) {
        line = line.replaceFirst(patternText, "").strip();
        JsonRepository.jsonArrayRepository.put(line, new JSONArray());
    }
}
