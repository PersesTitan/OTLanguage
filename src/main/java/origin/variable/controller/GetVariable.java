package origin.variable.controller;

import http.items.HttpRepository;
import origin.variable.model.Repository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//변수를 불러와서 대치하는 작업
public class GetVariable implements Repository {
    //:변수명( )
    String patternText = ":[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9_-]+( )";
    Pattern pattern = Pattern.compile(patternText);

    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    public String start(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String key = matcher.group().substring(1).trim(); //:변수명 => 변수명
            for (String keys : repository.keySet()) {
                if (repository.get(keys).containsKey(key))
                    line = line.replaceAll(patternText, repository.get(keys).get(key).toString());
                if (HttpRepository.partMap.containsKey(key))
                    line = line.replaceAll(patternText, HttpRepository.partMap.get(key));
            }
        }

        return line;
    }
}
