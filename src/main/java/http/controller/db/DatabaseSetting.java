package http.controller.db;

import http.items.Color;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseSetting {
    private final String dbKind; //sqlite, mariadb...
    private final String dbIp; //localhost
    private final String dbPort; //8080
    private final String dbName;

    public DatabaseSetting(String dbKind, String dbIp, String dbPort, String dbName) {
        this.dbKind = dbKind;
        this.dbIp = dbIp;
        this.dbPort = dbPort;
        this.dbName = dbName;
    }

    public void setting() {
        String dbUrl = String.format("jdbc:%s://%s:%s/%s", dbKind, dbIp, dbPort, dbName);
        try (Connection con = DriverManager.getConnection(dbUrl)) {

        } catch (SQLException ignored) {System.out.printf("%s디비 생성에 실페하였습니다.%s\n", Color.RED, Color.RESET);}
    }
}
