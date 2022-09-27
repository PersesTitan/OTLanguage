package bin.apply.sys.run;

import bin.token.MergeToken;
import work.StartWork;

import java.util.Map;
import java.util.regex.Pattern;

public class ForceQuit implements StartWork, MergeToken {
    private final Pattern pattern;

    public ForceQuit(String type) {
        this.pattern = Pattern.compile(startEndMerge(type));
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line, String origen, Map<String, Map<String, Object>>[] repositoryArray) {
        System.exit(0);
    }
}
