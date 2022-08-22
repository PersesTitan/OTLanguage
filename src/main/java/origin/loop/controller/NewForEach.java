package origin.loop.controller;

import etc.reader.ReadJSON;
import event.Setting;
import origin.exception.MatchException;
import origin.exception.MatchMessage;
import origin.exception.VariableException;
import origin.exception.VariableMessage;
import origin.loop.model.LoopWork;
import origin.variable.define.VariableCheck;
import origin.variable.define.VariableType;
import origin.variable.model.Repository;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewForEach implements LoopWork {
    private final String patternText = "^\\s*"+Setting.variableStyle+"\\^\\^\\[[^\\[\\]]+]";
    private final Pattern pattern = Pattern.compile(patternText);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            // 변수명^^[1, 2]
            String[] groups = matcher.group().strip().split("\\^\\^"); //변수명, [1, 2]
            String variableName = groups[0].trim(); //변수명
            String list = groups[1].trim(); //리스트
            String uuid = line.replaceFirst(patternText, "").strip();
            VariableType variableType = VariableCheck.checkVariableType(variableName);
            if (!VariableCheck.checkList(variableType, list))
                throw new VariableException(VariableMessage.typeMatchError);
            cut(list, variableName, uuid);
        }
    }

    //value : [1, 2]
    private void cut(String value, String var, String uuid) {
        if (!Repository.uuidMap.containsKey(uuid)) throw new MatchException(MatchMessage.grammarError);
        String total = Repository.uuidMap.get(uuid);
        //1, 2, 3, ...
        String[] values = value.trim().substring(1, value.length()-1).split(",");
        for (String vs : values) {
            VariableCheck.setValue(var, vs.trim());
            for (String l : total.split("\\n")) {
                BreakContinue breakContinue = settingStart(l);
                if (breakContinue.equals(BreakContinue.Break)) return;
                if (breakContinue.equals(BreakContinue.Continue)) break;
            }
        }
    }

    @Override
    public String getPattern() {
        return patternText;
    }
}
