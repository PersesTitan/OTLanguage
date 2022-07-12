package http.variable;

import http.items.Temporary;

import java.util.regex.Pattern;

import static origin.item.Setting.set;

public class WebPageVariable implements HttpWork, Temporary {
    //[url 경로] =^ㅇㅅㅇ^= [페이지 경로]
    private final String webPage = "^\\s*\\S+\\s+=\\^ㅇㅅㅇ\\^=\\s+\\S+";
    private final Pattern pattern = Pattern.compile(webPage);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    //페이지와 값 세팅
    @Override
    public void start(String line) {
        String[] values = line.split("=\\^ㅇㅅㅇ\\^=");
        String url = values[0].strip();
        String page = values[1].strip();
        pathMap.put(url, page);
    }
}
