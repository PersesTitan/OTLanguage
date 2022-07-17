package http.controller;

import http.items.HttpRepository;
import http.model.HttpWork;
import http.server.Server;

import java.util.regex.Pattern;

public class StartServer implements HttpRepository, HttpWork {
    //=[^ㅇㅅㅇ^]= [숫자]
    private final String serverPattern = "^\\s*<<ㅇㅅㅇ>>( [0-9]*)?";
    private final Pattern pattern = Pattern.compile(serverPattern);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line) {
        HttpRepository.startSetting();
        if (Pattern.compile("[0-9]").matcher(line).find()) {
            int port = Integer.parseInt(line.replaceAll("[^0-9]", ""));
            new Server(port);
        } else new Server();
    }
}
