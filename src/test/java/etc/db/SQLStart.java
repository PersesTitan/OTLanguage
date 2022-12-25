package etc.db;

import etc.db.exception.SQLExceptionTest;
import etc.db.item.CreateItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public interface SQLStart {
    // 조회
    default List<String[]> select(Statement stat, String tableName, String...columnNames) throws SQLException {
        ResultSet meta = stat.getConnection()
                .getMetaData()
                .getColumns(null, null, tableName, null);
        List<String> columns = new ArrayList<>();
        while (meta.next())
            columns.add(meta.getString("COLUMN_NAME"));

        // SQL
        String column = String.join(", ", columnNames);
        String sql = String.format("SELECT %s FROM %s", column, tableName);

        List<String[]> result = new ArrayList<>();
        ResultSet set = stat.executeQuery(sql);
        while (set.next()) {
            String[] rows = new String[columns.size()];
            ListIterator<String> list = columns.listIterator();
            while (list.hasNext())
                rows[list.nextIndex()] = set.getString(list.next());
            result.add(rows);
        }
        return result;
    }

    // UPDATE
    // 업데이트
    default void create(Statement stat, String tableName, CreateItem...items) throws SQLException {
        List<String> list = new ArrayList<>();
        for (CreateItem item : items)
            list.add(item.toString());
        String column = String.join(", ", list);
        String sql = String.format("CREATE TABLE %s (%s)", tableName, column);
        int count = stat.executeUpdate(sql);
        if (count != -1) throw new SQLExceptionTest().updateError();
    }

    // 테이블 삭제
    default void drop(Statement stat, String tableName) throws SQLException {
        String sql = String.format("DROP TABLE %s", tableName);
        int count = stat.executeUpdate(sql);
        if (count != -1) throw new SQLExceptionTest().updateError();
    }

    default void insert(Statement stat, String tableName, String...cols) throws SQLException {
        String col = String.join(", ", cols);
        String sql = String.format("INSERT INTO %s VALUES(%s)", tableName, col);
        stat.executeUpdate(sql);
    }

//    static void getType(Statement stat, String tableName) throws SQLException {
//        ResultSet result = stat.getConnection()
//                .getMetaData()
//                .getColumns(null, null, tableName, null);
//
//        List<String> types = new ArrayList<>();
//        while (result.next()) {
//            types.add(result.getString("TYPE_NAME"));
//        }
//        System.out.println(types);
//    }

    // 제거
    // conditions = 조건
    default void delete(Statement stat, String tableName, String conditions) throws SQLException {
        String condition = String.format("%s=", conditions);
        String sql = String.format("DELETE FROM %s WHERE %s", tableName, condition);
        stat.executeUpdate(sql);
    }

    // 업데이트
    default void update(Statement stat, String tableName) {

    }
}
