package http.controller.db.controller.sql;

import http.items.Color;

import java.sql.Connection;
import java.sql.SQLException;

public class DCL {

    public void commit(Connection con) throws SQLException {
        con.commit();
    }

    public void rollback(Connection con) throws SQLException {
        con.rollback();
    }
}
