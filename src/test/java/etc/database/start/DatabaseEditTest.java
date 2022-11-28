package etc.database.start;

import bin.apply.Setting;
import etc.database.DatabaseCreateTest;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

public class DatabaseEditTest extends StartWorkV3 {
    public DatabaseEditTest(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (DatabaseCreateTest.stmt == null) {
            Setting.warringMessage("데이터베이스가 생성되어 있지 않습니다.");

        } else {

        }
    }
}
