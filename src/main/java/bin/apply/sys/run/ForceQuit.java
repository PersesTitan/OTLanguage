package bin.apply.sys.run;

import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.apply.Setting.debugMode;

public class ForceQuit extends StartWorkV3 {
    public ForceQuit(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        // 디버깅 모드라면 종료 방지
        if (debugMode.isNoCompile()) System.exit(0);
    }
}
