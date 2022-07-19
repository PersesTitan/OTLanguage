package http.controller;

import http.items.HttpRepository;
import http.model.HttpWork;
import origin.exception.VariableException;
import origin.exception.VariableMessage;
import origin.variable.model.Repository;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class PortVariable implements HttpWork, Repository {
    //=(^ㅇㅅㅇ^)= [키]:[값]
    private final String webVar = "^\\s*<ㅇㅅㅇ> +[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9_-]+:[\\s\\S]+";
    private final Pattern pattern = Pattern.compile(webVar);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    //변수를 넣음
    @Override
    public void start(String line) {
        line = line.replaceFirst("^\\s*<ㅇㅅㅇ> +", "");
        StringTokenizer tokenizer = new StringTokenizer(line, ":");
        String key = tokenizer.nextToken().strip();
        String value = tokenizer.nextToken();
        if (set.contains(key)) throw new VariableException(key + VariableMessage.sameVariable);
        HttpRepository.partMap.put(key, value);
        set.add(key);
    }
}
