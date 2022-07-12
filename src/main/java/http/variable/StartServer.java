package http.variable;

import http.items.Temporary;
import http.server.Server;

import java.util.regex.Pattern;

public class StartServer implements Temporary, HttpWork {
    //=[^ㅇㅅㅇ^]= [숫자]
    private final String serverPattern = "^\\s*=\\[\\^ㅇㅅㅇ\\^]= ?\\d*";
    private final Pattern pattern = Pattern.compile(serverPattern);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line) {
        Temporary.startSetting();
        if (Pattern.compile("[^0-9]").matcher(line).find()) new Server();
        else {
            int port = Integer.parseInt(line.replaceAll("[^0-9]", ""));
            new Server(port);
        }
    }
}
