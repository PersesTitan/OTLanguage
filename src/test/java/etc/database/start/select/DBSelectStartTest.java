package etc.database.start.select;

import bin.apply.Setting;
import etc.database.DatabaseCreateTest;
import work.v3.StartWorkV3;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;

public class DBSelectStartTest extends StartWorkV3 implements DBSelectTest {
    // 3 // 0: SQL  // 1: 이름  // 2:리스트명
    public DBSelectStartTest(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (DatabaseCreateTest.stmt == null) {
            Setting.warringMessage("데이터베이스가 생성되어 있지 않습니다.");
            return;
        } else {
            try {
                var result = DatabaseCreateTest.stmt.executeQuery(params[0]);
                LinkedList<Object> list = getItems(result, params[1]);


            } catch (SQLException e) {
                throw new etc.database.exception.SQLException().sqlError();
            }
        }
    }
}
