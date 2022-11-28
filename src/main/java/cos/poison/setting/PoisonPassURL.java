package cos.poison.setting;

import work.v3.StartWorkV3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

import static cos.poison.PoisonRepository.passUrl;

public class PoisonPassURL extends StartWorkV3 {
    // 1
    public PoisonPassURL(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        passUrl.add(params[0].endsWith("/") ? params[0] : params[0] + "/");
    }
}
