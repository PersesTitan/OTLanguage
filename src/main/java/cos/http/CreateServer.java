package cos.http;

import bin.token.LoopToken;
import work.StartWork;

import java.util.Map;

public class CreateServer implements StartWork, LoopToken {
    private final String patternText = START + BLANK + SERVER ;

    @Override
    public boolean check(String line) {
        return false;
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {

    }
}
