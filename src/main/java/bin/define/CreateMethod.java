package bin.define;

import bin.token.LoopToken;
import work.StartWork;

import java.util.Map;

public class CreateMethod implements LoopToken, StartWork {

    public CreateMethod(String type) {

    }

    @Override
    public boolean check(String line) {
        return false;
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {

    }
}
