package cos.poison;

import bin.token.LoopToken;
import cos.http.controller.HttpMethod;
import cos.poison.controller.HttpServerManager;
import cos.poison.method.PoisonMethod;
import cos.poison.root.VariableHTML;
import cos.poison.setting.PoisonCreate;
import cos.poison.setting.PoisonStart;
import work.StartWork;

import java.util.Map;

import static bin.apply.Repository.startWorks;

public class Poison implements StartWork, LoopToken, PoisonRepository {
    private final String className;
    public static HttpServerManager httpServerManager = new HttpServerManager();
    public static VariableHTML variableHTML = new VariableHTML(MODEL);

    public Poison(String className) {
        this.className = className;
        first();
    }

    @Override
    public boolean check(String line) {
        return false;
    }

    @Override
    public void start(String line, String origen, Map<String, Map<String, Object>>[] repositoryArray) {}

    @Override
    public void first() {
        startWorks.add(new PoisonCreate(className));
        startWorks.add(new PoisonStart(className));
        startWorks.add(new PoisonMethod(className, LoopToken.POISON_GET, HttpMethod.GET));
        startWorks.add(new PoisonMethod(className, LoopToken.POISON_POST, HttpMethod.POST));
    }
}
