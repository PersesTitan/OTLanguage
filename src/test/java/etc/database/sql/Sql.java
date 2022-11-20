package etc.database.sql;

import etc.database.item.SQL;

import java.sql.SQLException;
import java.sql.Statement;

public class Sql {
    public void start(Statement stat, String sql, SQL sqlItem) throws SQLException {
        switch (sqlItem) {
            case ALTER, GRANT, COMMIT, ROLLBACK, REVOKE, TRUNCATE -> stat.execute(sql);
            case INSERT, UPDATE, DELETE, CREATE, DROP -> stat.executeUpdate(sql);
            case SELECT -> stat.executeQuery(sql);
        }
    }
}
