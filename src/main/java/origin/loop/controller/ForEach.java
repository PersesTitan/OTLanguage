package origin.loop.controller;

import event.Setting;
import origin.exception.MatchException;
import origin.exception.MatchMessage;
import origin.exception.VariableException;
import origin.exception.VariableMessage;
import origin.loop.model.LoopWork;
import origin.variable.define.VariableCheck;
import origin.variable.model.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForEach extends Setting implements LoopWork {
    private final String patternText = "^\\s*[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9_-]+\\^\\^[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9_-]+";
    private final Pattern pattern = Pattern.compile(patternText);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            //괄호 아이디 분리 작업
            List<String> listInKey = Arrays.stream(line.strip().split(" "))
                    .filter(Objects::nonNull)
                    .filter(v -> !v.isEmpty())
                    .toList();
            String key = listInKey.get(1);
            if (!Repository.uuidMap.containsKey(key) || listInKey.size() != 2) throw new MatchException(MatchMessage.grammarError);

            //변수 분리 작업
            //변수1, 변수2
            var groups = matcher.group().strip().split("\\^\\^");
            var variable1 = groups[0];  //넣을 값
            var variable2 = groups[1];  //리스트 변수
            if (VariableCheck.check(variable1)) throw new VariableException(VariableMessage.noHaveVarName);
            if (VariableCheck.check(variable2)) throw new VariableException(VariableMessage.noHaveVarName);
            //2개 타입 일치 확인
            char c1 = VariableCheck.getCheck(variable1).charAt(1);
            char c2 = VariableCheck.getCheck(variable2).charAt(1);
            if (c1 != c2) throw new VariableException(VariableMessage.typeMatchError);
            //2개 list, 변수 일치 확인
            c1 = VariableCheck.getCheck(variable1).charAt(0);
            c2 = VariableCheck.getCheck(variable2).charAt(0);
            if (!(c1 == 'ㅇ' || c1 == 'o')) throw new VariableException(VariableMessage.typeMatchError);
            if (!(c2 == 'ㄹ' || c2 == 'l')) throw new VariableException(VariableMessage.typeMatchError);

            var list = VariableCheck.getArray(variable2);
            if (list == null) throw new VariableException(variable2 + VariableMessage.doNotFind);

            String value = Repository.uuidMap.get(key).substring(1, Repository.uuidMap.get(key).length()-1).strip();
            for (Object ob : list) {
                VariableCheck.setValue(variable1, ob);
                for (String l : value.split("\\n")) super.start(l);
            }
        }
    }

    @Override
    public String getPattern() {
        return patternText;
    }
}
