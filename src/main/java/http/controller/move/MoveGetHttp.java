package http.controller.move;

import http.items.HttpRepository;
import http.model.HttpMoveWork;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//페이지 이동 GET 등록
public class MoveGetHttp implements HttpMoveWork, HttpRepository {
    //[API] <<ㅇㅅㅇ< [이동할 페이지]
    private final String patternText = "^\\s*\\S+\\s*<<ㅇㅅㅇ<\\s*\\S+";
    private final Pattern pattern = Pattern.compile(patternText);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void move(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String[] texts  = matcher.group().split("\\s*<<ㅇㅅㅇ<\\s*");
            String api = texts[0];
            String movePage = texts[1];
//            GetMove.put(api, movePage);
        }
    }
}
