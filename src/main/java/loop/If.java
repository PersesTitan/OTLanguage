package loop;

import item.Setting;
import item.work.LoopWork;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class If extends Setting implements LoopWork {
    private static final String SPECIFIED = "?ㅅ?";
    //시작 또는 줄바꿈 [공백] ?ㅅ? [공백 1개 이상]
    static final String patternText = "(\\n|^)\\s*\\?ㅅ\\?\\s+";
    private static final Pattern pattern = Pattern.compile(patternText);

    @Override
    public void start(String line) throws Exception {

        StringTokenizer tokenizer = new StringTokenizer(line);
        tokenizer.nextToken();
        String bool = tokenizer.nextToken();
        String token = tokenizer.nextToken();

        if (bool.equals("ㅇㅇ")) {
            for (String lines : uuidMap.get(token).split("\\n"))
                super.start(lines);
        }
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }
}
