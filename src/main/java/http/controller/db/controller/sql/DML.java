package http.controller.db.controller.sql;

import http.items.Color;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DML {

    public List<String> select(Connection con, String sql, String values) {
        List<String> list = new ArrayList<>();
        try (var stat= con.createStatement()) {
            var ret = stat.executeQuery(sql);
            while (ret.next()) list.add(ret.getString(values));
        } catch (SQLException e) {
            System.out.printf("%s%s에 실패하였습니다.%s\n", Color.RED, "SELECT", Color.RESET);
        }
        return list;
    }

    public void insert(Connection con, String sql) {
        try (var stat = con.createStatement()) {
            stat.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.printf("%s%s에 실패하였습니다.%s\n", Color.RED, "INSERT", Color.RESET);
        }
    }

    public void update(Connection con, String sql) {
        try (var stat = con.createStatement()){
            stat.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.printf("%s%s에 실패하였습니다.%s\n", Color.RED, "UPDATE", Color.RESET);
        }
    }

    public void delete(Connection con, String sql) {
        try (var stat = con.createStatement()){
            stat.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.printf("%s%s에 실패하였습니다.%s\n", Color.RED, "DELETE", Color.RESET);
        }
    }
}
