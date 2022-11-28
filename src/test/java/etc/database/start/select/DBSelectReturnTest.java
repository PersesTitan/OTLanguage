package etc.database.start.select;

import bin.apply.Setting;
import etc.database.DatabaseCreateTest;
import work.v3.ReturnWorkV3;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;

public class DBSelectReturnTest extends ReturnWorkV3 implements DBSelectTest {
    // 2 // 0: SQL  // 1: 이름
    public DBSelectReturnTest(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (DatabaseCreateTest.stmt == null) {
            Setting.warringMessage("데이터베이스가 생성되어 있지 않습니다.");
            return null;
        } else {
            try {
                var result = DatabaseCreateTest.stmt.executeQuery(params[0]);
                LinkedList<Object> list = getItems(result, params[1]);
                return list.toString();
            } catch (SQLException e) {
                throw new etc.database.exception.SQLException().sqlError();
            }
        }
    }
}
