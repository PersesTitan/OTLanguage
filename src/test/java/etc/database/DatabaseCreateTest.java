package etc.database;

import bin.apply.sys.item.SystemSetting;
import org.h2.jdbc.JdbcSQLDataException;
import work.v3.StartWorkV3;

import java.sql.*;
import java.util.LinkedList;
import java.util.Map;

public class DatabaseCreateTest extends StartWorkV3 {
    public static void main(String[] args) {
        try {
            var con= DriverManager.getConnection("jdbc:h2:tcp://localhost/~/jpashop", "sa", "");
            var stat = con.createStatement();
            var rs = stat.executeQuery("SELECT * FROM TEST");
            while (rs.next()) {
                int i = 1;
                while (true) {
                    try {
                        System.out.println(rs.getString(i++));
                    } catch (JdbcSQLDataException e) {
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private final String klassName;
    public static Statement stmt;

    // 1, 3
    public DatabaseCreateTest(String klassName, int... counts) {
        super(counts);
        this.klassName = klassName;
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        // url  // url, user, password
        try {
            String url = params[0];
            Connection con = params.length == 1
                    ? DriverManager.getConnection(url)
                    : DriverManager.getConnection(url, params[1], params[2]);
            stmt = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
