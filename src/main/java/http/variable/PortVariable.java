package http.variable;

import http.items.Temporary;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

import static origin.item.Setting.set;

public class PortVariable implements HttpWork, Temporary {
    //=(^ㅇㅅㅇ^)= [키]:[값]
    private final String webVar = "^\\s*=\\(\\^ㅇㅅㅇ\\^\\)= \\S+:\\S+";
    private final Pattern pattern = Pattern.compile(webVar);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    //변수를 넣음
    @Override
    public void start(String line) throws Exception {
        line = line.replaceFirst("\\d*=\\(\\^ㅇㅅㅇ\\^\\)= ", "");
        StringTokenizer tokenizer = new StringTokenizer(line, ":");
        String key = tokenizer.nextToken().strip();
        String value = tokenizer.nextToken();

        if (set.contains(key)) throw new Exception("이미 존재하는 변수 이름 입니다.");
        portMap.put(key, value);
        set.add(key);
    }
}
