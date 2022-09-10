package origin.loop.controller;

import event.Setting;
import origin.exception.MatchException;
import origin.exception.MatchMessage;
import origin.loop.model.LoopWork;
import origin.variable.model.Repository;

import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class If extends Setting implements LoopWork, Repository {
    //시작 또는 줄바꿈 [공백] ?ㅅ? [공백 1개 이상]
    private static final String uuidPattern = "[a-zA-Z0-9-]+";
    private static final String patternText =
            "(\\n|^)\\s*\\?ㅅ\\?\\s+(ㅇㅇ|ㄴㄴ)\\s+"+uuidPattern+ //if
                    "(\\s+\\?ㅈ\\?\\s+(ㅇㅇ|ㄴㄴ)\\s+"+uuidPattern+")*"+   //else if
                    "(\\?ㅉ\\?\\s+"+uuidPattern+")?"; //else
    private static final Pattern pattern = Pattern.compile(patternText);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line) {
        for (String l : lineStart(line).split("\\n")) {
            Setting.start(l);
        }
    }

    //라인을 반환함
    public static String lineStart(String line) {
        if (pattern.matcher(line).find()) {
            StringTokenizer tokenizer = new StringTokenizer(line);
            while (tokenizer.hasMoreTokens()) {
                String sing = tokenizer.nextToken();
                switch (sing) {
                    case "?ㅅ?":
                    case "?ㅈ?":
                        if (tokenizer.nextToken().equals("ㅇㅇ"))
                            return checkUuid(tokenizer.nextToken());
                        break;
                    case "?ㅉ?":
                        return checkUuid(tokenizer.nextToken());
                }
            }
        }
        return "";
    }

    private static String checkUuid(String key) {
        if (uuidMap.containsKey(key)) return uuidMap.get(key);
        else throw new MatchException(MatchMessage.grammarError);
    }

    @Override
    public String getPattern() {
        return patternText;
    }
}
