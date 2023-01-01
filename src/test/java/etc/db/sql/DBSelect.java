package etc.db.sql;

import bin.apply.Setting;
import etc.db.exception.SQLExceptionTest;
import work.v3.ReturnWorkV3;
import work.v3.StartWorkV3;
import work.v4.ReturnWork;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

import static etc.db.start.DBCreate.stat;

public class DBSelect extends StartWorkV3 {
    public DBSelect(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (stat != null) {
            try (ResultSet result = stat.executeQuery(params[0])) {
                while (result.next()) {
                    int row = result.getRow();  // 현재 라인
                    System.out.print(row);
                    System.out.print(" | ");
                    System.out.print(result.getArray(row));
                    System.out.print(" | ");
                    System.out.println(Arrays.toString(result.getBytes(row)));
                }
            } catch (SQLException e) {
                throw new SQLExceptionTest().sqlError();
            }
        } else Setting.warringMessage("데이터베이스가 생성되지 않았습니다.");
    }
}
