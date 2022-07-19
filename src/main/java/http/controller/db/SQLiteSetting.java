package http.controller.db;

import http.items.Color;
import org.sqlite.JDBC;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteConnection;
import origin.exception.DBException;
import origin.exception.DBMessage;

import java.sql.*;
import java.util.Properties;

public class SQLiteSetting {
    private final String dbKind = "sqlite"; //sqlite, mariadb...
    private final String url;
    private final Connection con;

    // sqlite localhost 8080
    public SQLiteSetting(String dbIp, String dbPort, String dbName) {
        this.url = String.format("jdbc:%s://%s:%s/%s", this.dbKind, dbIp, dbPort, dbName);
        try {
            this.con = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.printf("%s디비 생성에 실페하였습니다.%s", Color.RED, Color.RESET);
            throw new DBException(DBMessage.error);
        }
    }

    public SQLiteSetting(String dbIp, String dbPort, String dbName, String userName, String password) {
        this.url = String.format("jdbc:%s://%s:%s/%s", this.dbKind, dbIp, dbPort, dbName);
        try {
            this.con = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            System.out.printf("%s디비 생성에 실페하였습니다.%s", Color.RED, Color.RESET);
            throw new DBException(DBMessage.error);
        }
    }

    private PreparedStatement setting(String url) {
        try (PreparedStatement pres = con.prepareStatement(url)) {
            return pres;
        } catch (SQLException e) {
            System.out.printf("%s디비 생성에 실페하였습니다.%s", Color.RED, Color.RESET);
            throw new DBException(DBMessage.error);
        }
    }

    public void select(String sql, String ... os) {
        try (var values = setting(sql).executeQuery()) {
            for (String o : os) {
                String value = values.getString(o);

            }
        } catch (SQLException e) {
            System.out.printf("%s디비 생성에 실페하였습니다.%s", Color.RED, Color.RESET);
            throw new DBException(DBMessage.error);
        }
    }

    public void insert(String sql) {
        try (var values = setting(sql).executeQuery(sql)) {

        } catch (SQLException e) {
            System.out.printf("%s디비 생성에 실페하였습니다.%s", Color.RED, Color.RESET);
            throw new DBException(DBMessage.error);
        }
    }
}
