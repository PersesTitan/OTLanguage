package loop;

import item.Setting;
import item.work.LoopWork;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class If extends Setting implements LoopWork {
    private static final String SPECIFIED = "?ㅅ?";
    //시작 또는 줄바꿈 [공백] ?ㅅ? [공백 1개 이상]
    static final String patternText = "(\\n|^)\\s*\\?ㅅ\\?\\s+";
    private static final Pattern pattern = Pattern.compile(patternText);

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

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }
}
