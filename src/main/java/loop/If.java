package loop;

import item.Check;
import item.Setting;
import item.work.LoopWork;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class If extends Setting implements Check, LoopWork {
    private static final String SPECIFIED = "?ㅅ?";
    private final String patternText = "\\n\\s*\\?ㅅ\\?\\s|^\\s*\\?ㅅ\\?\\s";
    private final Pattern pattern = Pattern.compile(patternText);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line) throws Exception {
        //?ㅅ? 제거 작업
        line = line.strip().substring(SPECIFIED.length());
        StringTokenizer tokenizer = new StringTokenizer(line);
        while(tokenizer.hasMoreTokens()) {
            
        }
        line = line.replace("ㅇㅇ", "true");
        line = line.replace("ㄴㄴ", "false");
        if (changeBool(line)) {
            //동작 적는 곳
        } else {
            //false 일때의 동작
        }
    }
}
