package cos.poison.setting;

import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.apply.Controller.allLoop;
import static bin.apply.Setting.debugMode;
import static cos.poison.setting.PoisonCreate.httpServerManager;

public class PoisonStart extends StartWorkV3 {
    public PoisonStart(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        httpServerManager.start();
        if (debugMode.isCompile()) httpServerManager.stop();
        else allLoop();
    }
}
