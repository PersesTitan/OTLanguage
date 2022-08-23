package origin.variable.json.controller.json.controller;

import etc.reader.ReadJSON;
import event.Setting;
import event.token.Token;
import origin.exception.MatchException;
import origin.exception.MatchMessage;
import origin.exception.VariableMessage;
import origin.loop.model.LoopWork;
import origin.variable.json.controller.json.define.JsonRepository;
import origin.variable.model.Repository;
import origin.variable.model.VariableWork;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static event.token.Token.*;
import static origin.variable.model.Repository.makeJsonWorks;

public class OutPutJson implements
        LoopWork, JsonRepository, VariableWork {
    private final String text;
    private final String patternText;
    private final Pattern pattern;

    // ㅈㄷㅈ 변수명 {...} => 변수명
    public OutPutJson(String patternText,
                      Map<String, Map<String, Object>> repository) {
        this.text = patternText;
        this.patternText = makeBlank(START, patternText, BLANK, VARIABLE_NAME);
        this.pattern = Pattern.compile(
                makeBlank(START, patternText, BLANK, VARIABLE_NAME, UUID, "=>", VARIABLE_NAME, END));
        repository.put(patternText, new HashMap<>());
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line,
                      Map<String, Map<String, Object>> repository,
                      Set<String> set) {
        set.removeAll(jsonArrayRepository.keySet());
        set.removeAll(jsonObjectRepository.keySet());
        // ㅈㄷㅈ 변수명 {...} => 변수명
        jsonArrayRepository.clear();
        jsonObjectRepository.clear();
        // 변수명 {...} => 변수명
        line = line
                .replaceFirst("^\\s*" + JSON, "")
                .strip();

        String k = getPatternValue(line, VARIABLE_NAME); //변수명
        // {...} => 변수명
        line = setDelete(line, VARIABLE_NAME);

        String uuid = getPatternValue(line, UUID);   //uuid
        line = setDelete(line, UUID);                // => 변수명
        line = setDelete(line, "=>");    // 변수명
        String variableName = line.trim();
        String total = Repository.uuidMap.get(uuid);

        point:
        for (var l : total.split("\\n")) {
            l = Setting.startString(l, repository, set);
            for (var work : makeJsonWorks) {if (work.check(l)) {work.start(l); continue point;}}
            Setting.start(l, repository, set);
        }

        repository.get(JSON).put(k, getRepositoryValue(":" + variableName + "_"));
        set.add(k);
    }

    @Override
    public String getPattern() {
        return patternText;
    }

    private String getPatternValue(String line, String patternText) {
        Matcher matcher = Pattern.compile("^\\s*" + patternText).matcher(line);
        if (matcher.find()) return matcher.group();
        else throw new MatchException(MatchMessage.grammarError);
    }

    private String setDelete(String line, String deletePattern) {
        return line.replaceFirst("^\\s*" + deletePattern, "").trim();
    }
}
