package cos.poison.v3;

import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static cos.poison.Poison.httpServerManager;

public class PoisonStart extends StartWorkV3 {
    public PoisonStart(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        httpServerManager.start();
    }
}
