package http.controller.db.controller.sql;

import http.items.Color;

import java.sql.Connection;
import java.sql.SQLException;

public class DDL {

    public void create(Connection con, String sql) throws SQLException {
        var stat = con.createStatement();
        stat.execute(sql);
    }

    public void alter(Connection con, String sql) throws SQLException {
        var stat = con.createStatement();
        stat.execute(sql);
    }

    public void drop(Connection con, String sql) throws SQLException {
        var stat = con.createStatement();
        stat.execute(sql);
    }
}
