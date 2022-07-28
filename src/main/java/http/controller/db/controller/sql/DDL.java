package http.controller.db.controller.sql;

import http.items.Color;

import java.sql.Connection;
import java.sql.SQLException;

public class DDL {

    public void create(String sql, Connection con) {
        try (var stat = con.createStatement()) {
            stat.execute(sql);
        } catch (SQLException ignored) {
            System.out.printf("%s%s에 실패하였습니다.%s\n", Color.RED, "CREATE", Color.RESET);
        }
    }

    public void alter(String sql, Connection con) {
        try (var stat = con.createStatement()) {
            stat.execute(sql);
        } catch (SQLException ignored) {
            System.out.printf("%s%s에 실패하였습니다.%s\n", Color.RED, "ALTER", Color.RESET);
        }
    }

    public void drop(String sql, Connection con) {
        try (var stat = con.createStatement()) {
            stat.execute(sql);
        } catch (SQLException ignored) {
            System.out.printf("%s%s에 실패하였습니다.%s\n", Color.RED, "DROP", Color.RESET);
        }
    }
}
