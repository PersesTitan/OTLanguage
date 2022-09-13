package bin.orign.loop;

import bin.token.Token;
import work.StartWork;

import java.util.Map;

public class For implements Token, StartWork {

    @Override
    public boolean check(String line) {
        return false;
    }

    @Override
    public void start(String line, String origen, Map<String, Map<String, Object>>[] repositoryArray) {

    }
}
