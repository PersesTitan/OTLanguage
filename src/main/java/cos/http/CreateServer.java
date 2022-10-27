package cos.http;

import bin.token.LoopToken;
import work.StartWork;

import java.util.LinkedList;
import java.util.Map;

public class CreateServer implements StartWork, LoopToken {
    private final String patternText = START + BLANK + SERVER ;

    @Override
    public boolean check(String line) {
        return false;
    }

    @Override
    public void start(String line, String origen,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {

    }

    @Override
    public void first() {

    }
}
