package custom.registration.controller;

import custom.registration.define.RepositoryValue;
import custom.use.controller.UseCustomClass;
import custom.use.define.BracketSplit;
import event.Setting;
import origin.exception.VariableException;
import origin.exception.VariableMessage;
import origin.variable.model.Repository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//커스텀 클래스 등록
public class DefineCustomClass implements BracketSplit {
    //ㅋㅅㅋ 클래스명[ㅇㅁㅇ 변수명] uuid
    private final String text; //ㅋㅅㅋ
    private final String patternText;
    private final String uuidText; //ㅋㅅㅋ 클래스명[ㅇㅁㅇ 변수명]
    private final Pattern pattern;

    public DefineCustomClass(String patternText) {
        //builder : [ㅇㅁㅇ 변수명]
        StringBuilder builder = new StringBuilder("(\\[\\s*(");
        Repository.repository.keySet().forEach(key -> builder.append(key).append("|"));
        builder.deleteCharAt(builder.length()-1);
        builder.append(")\\s+").append(Setting.variableStyle).append("\\s*])+");
        //[ㅇㅁㅇ 변수명]
        this.patternText = builder.toString();
        // ㅋㅅㅋ 클래스명[ㅇㅁㅇ 변수명]
        this.uuidText = "^\\s*" + patternText + "\\s+" + Setting.variableStyle + this.patternText;
        this.pattern = Pattern.compile(this.uuidText);
        this.text = patternText;
    }

    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    public void start(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String group = matcher.group().strip(); //ㅋㅅㅋ 클래스명[ㅇㅁㅇ 변수명]
            RepositoryValue variables = getRepositoryValue(group, patternText); //변수타입, 변수명
            String className = group //클래스명 추출
                    .replaceFirst(patternText, "")
                    .replaceFirst("^\\s*"+this.text+"\\s*", "")
                    .strip();

            if (Repository.classNames.contains(className)) //중복 검사
                throw new VariableException(className + VariableMessage.sameMethod);
            Repository.classNames.add(className);
            // uuid 추출하기
            String uuid = line.replaceFirst(uuidText, "").strip();
            if (Repository.uuidMap.containsKey(uuid)) {
                String total = Repository.uuidMap.get(uuid);
                Repository.customClass.add(new UseCustomClass(className, variables, total));
            }
        }
    }
}
