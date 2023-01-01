package etc.db.start;

import etc.db.exception.SQLExceptionTest;
import work.v3.StartWorkV3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Map;

public class DBCreate extends StartWorkV3 {
    public static Statement stat;
    // 1: url
    // 3: url, user, password
    public DBCreate(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        try {
            final Connection con = params.length == 1
                    ? DriverManager.getConnection(params[0])
                    : DriverManager.getConnection(params[0], params[1], params[2]);
            stat = con.createStatement();
        } catch (SQLException e) {
            throw new SQLExceptionTest().sqlCreateError();
        }
    }
}
