package origin.variable.controller;

import http.items.HttpRepository;
import origin.exception.VariableException;
import origin.exception.VariableMessage;
import origin.variable.model.Repository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//변수에 값을 넣는 작업
public class SetVariable implements Repository {
    String patternText = "^\\s*[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9_-]+:";
    Pattern pattern = Pattern.compile(patternText);

    public boolean check(String line) {
        Pattern pattern = Pattern.compile(patternText + "[^:]+");
        return pattern.matcher(line).find();
    }

    public void start(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String originValue = matcher.group().strip(); //변수명:
            String key = originValue.substring(0, originValue.length()-1); //변수명: => 변수명
            String value = line.replaceFirst(patternText, "");
            if (!set.contains(key)) throw new VariableException(key + VariableMessage.doNotFind);
            if (HttpRepository.partMap.containsKey(key)) HttpRepository.partMap.put(key, value);
            for (String keys : repository.keySet()) {
                if (repository.get(keys).containsKey(key))
                    repository.get(keys).put(key, value.strip()); //변수명:값 => 값을 넣는 작업
            }
        }
    }
}
