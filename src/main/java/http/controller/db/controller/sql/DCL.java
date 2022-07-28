package http.controller.db.controller.sql;

import http.items.Color;

import java.sql.Connection;
import java.sql.SQLException;

public class DCL {

    public void commit(Connection con) {
        try {
            con.commit();
        } catch (SQLException ignored) {
            System.out.printf("%s%s에 실패하였습니다.%s\n", Color.RED, "COMMIT", Color.RESET);
        }
    }

    public void rollback(Connection con) {
        try {
            con.rollback();
        } catch (SQLException e) {
            System.out.printf("%s%s에 실패하였습니다.%s\n", Color.RED, "ROLLBACK", Color.RESET);
        }
    }
}
