package bin.apply.sys.run;

import bin.token.MergeToken;
import work.StartWork;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForceQuit implements StartWork, MergeToken {

    @Override
    public boolean check(String line) {
        return false;
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {
        System.exit(0);
    }

    @Override
    public void first() {

    }
}
