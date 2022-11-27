package etc.database.start;

import etc.database.DatabaseCreateTest;
import work.v3.ReturnWorkV3;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;

public class DatabaseSelectTest extends ReturnWorkV3 {
    public DatabaseSelectTest(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (DatabaseCreateTest.stmt == null) {

        } else {
            try {
                var result = DatabaseCreateTest.stmt.executeQuery(params[0]);
                
            } catch (SQLException e) {
                throw new etc.database.exception.SQLException().sqlError();
            }
        }
        return null;
    }
}
