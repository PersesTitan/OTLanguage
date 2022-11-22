package etc.database.start;

import work.v3.StartWorkV3;

import java.sql.*;
import java.util.LinkedList;
import java.util.Map;

public class DatabaseSQLTest extends StartWorkV3 {
    private final Connection con;

    public DatabaseSQLTest(Connection con, int... counts) {
        super(counts);
        this.con = con;
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
//        if (params.length < 1) throw new MatchException().grammarError();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(params[0])) {

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
