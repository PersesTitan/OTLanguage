package http.controller;

import http.items.HttpRepository;
import http.server.Server;
import origin.exception.ServerException;
import origin.exception.ServerMessage;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//GET 과 POST 값 가져오는 작업
public class HttpGetPost implements HttpRepository {
    //[경로] [POST,GET]>ㅇㅅㅇ>[name]
    private final String patternText = "\\S+\\s+(POST|GET)\\s*>ㅇㅅㅇ>\\s*\\S+";
    private final Pattern pattern = Pattern.compile(patternText);

    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    public String start(String line) {
        if (Server.httpServerManager == null) throw new ServerException(ServerMessage.nullServer);
        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            String text = matcher.group();
            var texts = text.split(">ㅇㅅㅇ>");
            StringTokenizer pathAndMethod = new StringTokenizer(texts[0].strip()); // [path] [POST, GET]
            String name = texts[1].strip(); // [name]
            String path = pathAndMethod.nextToken(); // [path]
            String method = pathAndMethod.nextToken(); // [POST, GET]

            String postMethod = "POST";
            if (method.equals(postMethod)) line = line.replaceFirst(patternText, POST.get(path).getOrDefault(name, "").toString());
            else line = line.replaceFirst(patternText, GET.get(path).getOrDefault(name, "").toString());
        }
        return line;
    }
}
