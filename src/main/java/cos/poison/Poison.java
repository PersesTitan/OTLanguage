package cos.poison;

import bin.token.LoopToken;
import cos.poison.controller.HttpServerManager;
import cos.poison.method.PoisonGet;
import cos.poison.method.PoisonPost;
import cos.poison.root.VariableHTML;
import cos.poison.setting.PoisonCreate;
import cos.poison.setting.PoisonStart;
import work.StartWork;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static bin.apply.Setting.runMessage;

public class Poison implements StartWork, LoopToken {
    public static HttpServerManager httpServerManager = new HttpServerManager();
    public static VariableHTML variableHTML = new VariableHTML(MODEL);
    private final List<StartWork> startWorks = new ArrayList<>();

    private final String className;

    public Poison(String className) {
        this.className = className;

        startWorks.add(new PoisonCreate(className));
        startWorks.add(new PoisonStart(className));
        startWorks.add(new PoisonGet(className));
        startWorks.add(new PoisonPost(className));
    }

    @Override
    public boolean check(String line) {
        return line.strip().startsWith(className);
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {
        for (var works : startWorks) {if (works.check(line)) {works.start(line, origen, repositoryArray); return;}}
        runMessage(origen);
    }
}
