package origin.loop.controller;

import event.Setting;
import origin.loop.model.LoopWork;
import origin.variable.model.Repository;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class If extends Setting implements LoopWork, Repository {
    //시작 또는 줄바꿈 [공백] ?ㅅ? [공백 1개 이상]
    private static final String patternText = "(\\n|^)\\s*\\?ㅅ\\?\\s+";
    private static final Pattern pattern = Pattern.compile(patternText);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line);
        tokenizer.nextToken();
        String bool = tokenizer.nextToken();
        String token = tokenizer.nextToken();

        if (bool.equals("ㅇㅇ")) {
            for (String lines : uuidMap.get(token).split("\\n")) super.start(lines);
        }
    }

    @Override
    public String getPattern() {
        return patternText;
    }
}
