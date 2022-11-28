package etc.database.start.select;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public interface DBSelectTest {
    default LinkedList<Object> getItems(ResultSet resultSet, String queryName) throws SQLException {
        LinkedList<Object> list = new LinkedList<>();
        while (resultSet.next()) list.add(resultSet.getObject(queryName));
        return list;
    }
}
