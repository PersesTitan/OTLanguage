package cos.poison.setting;

import bin.token.LoopToken;
import work.StartWork;

import java.util.Map;
import java.util.regex.Pattern;

import static cos.poison.Poison.httpServerManager;

public class PoisonStart implements LoopToken, StartWork {
    private final Pattern pattern;

    public PoisonStart(String className) {
        this.pattern = Pattern.compile(startEndMerge(className, ACCESS, POISON_START));
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {
        httpServerManager.start();
    }
}
