package http.controller.db.controller.sql;

import http.items.Color;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DML {

    public List<String> select(Connection con, String sql, String values) throws SQLException {
        List<String> list = new ArrayList<>();
        var stat= con.createStatement();
        var ret = stat.executeQuery(sql);
        while (ret.next()) list.add(ret.getString(values));
        return list;
    }

    public void insert(Connection con, String sql) throws SQLException {
        var stat = con.createStatement();
        stat.executeUpdate(sql);
    }

    public void update(Connection con, String sql) throws SQLException {
        var stat = con.createStatement();
        stat.executeUpdate(sql);
    }

    public void delete(Connection con, String sql) throws SQLException {
        var stat = con.createStatement();
        stat.executeUpdate(sql);
    }
}
