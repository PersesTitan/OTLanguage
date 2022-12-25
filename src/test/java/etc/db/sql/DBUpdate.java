package etc.db.sql;

import bin.apply.Setting;
import etc.db.exception.SQLExceptionTest;
import work.v3.StartWorkV3;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;

import static etc.db.start.DBCreate.stat;

public class DBUpdate extends StartWorkV3 {
    // 1: sql
    public DBUpdate(int... counts) {
        super(counts);
    }

    // Insert, Delete, Update
    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (stat != null) {
            try {
                int i = stat.executeUpdate(params[0]);
                if (i == 0) throw new SQLExceptionTest().updateError();
            } catch (SQLException e) {
                throw new SQLExceptionTest().sqlError();
            }
        } else Setting.warringMessage("데이터베이스가 생성되지 않았습니다.");
    }
}
