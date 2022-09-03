package http.controller;

import event.Setting;
import http.items.Color;
import http.items.HttpRepository;
import http.model.HttpWork;
import origin.exception.FileFailException;
import origin.exception.FileFailMessage;

import java.io.File;
import java.util.regex.Pattern;

public class HTMLVariable implements HttpWork, HttpRepository {
    //[url 경로] =^ㅇㅅㅇ^= [페이지 경로]
    private final String webPage;
    private final Pattern pattern;

    public HTMLVariable(String pattern) {
        this.webPage = "^\\s*\\S+\\s+"+pattern+"\\s+\\S+";
        this.pattern = Pattern.compile(webPage);
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    //페이지와 값 세팅
    @Override
    public void start(String line) {
        String[] values = line.split("<ㅇㅅㅇ<");
        String url = values[0].strip();
        String page = values[1].strip();
        String pages = Setting.path.equals("") ? page : Setting.path + "/" + page;
        if (!new File(pages).exists()) {
            System.out.printf("%s%s%s\n", Color.RED, FileFailMessage.doNotFindFile, Color.RESET);
            throw new FileFailException(FileFailMessage.doNotFindFile);
        }
        pathMap.put(url, pages);
    }
}
