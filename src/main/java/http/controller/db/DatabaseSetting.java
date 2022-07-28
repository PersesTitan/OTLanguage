package http.controller.db;

import http.controller.db.controller.sql.DCL;
import http.controller.db.controller.sql.DDL;
import http.controller.db.controller.sql.DML;
import http.controller.db.define.DBType;
import http.items.Color;
import origin.exception.MatchException;
import origin.exception.MatchMessage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.StringTokenizer;

public class DatabaseSetting {
    private final static DCL dcl = new DCL();
    private final static DDL ddl = new DDL();
    private final static DML dml = new DML();
    private final String dbUrl;
    private final String user;
    private final String password;

    public DatabaseSetting(String dbKind, String dbIp, String dbPort, String dbName, String user, String password) {
        this.user = user;
        this.password = password;
        //sqlite, mariadb... //localhost //9090
        dbUrl = String.format("jdbc:%s://%s:%s/%s", dbKind, dbIp, dbPort, dbName);
    }

    public DatabaseSetting(String url, String user, String password) {
        this.user = user;
        this.password = password;
        this.dbUrl = url;
    }

    //데이터베이스 설정
    //sql = SELECT * FROM TEST
    public void setting(String sql) {
        try (Connection con = DriverManager.getConnection(dbUrl, user, password)) {
            StringTokenizer tokenizer = new StringTokenizer(sql);
            String kind = tokenizer.nextToken(); //commit, select, ...

            if (kind.equalsIgnoreCase(DBType.DCL.COMMIT.name())) dcl.commit(con); //DCL
            else if (kind.equalsIgnoreCase(DBType.DCL.ROLLBACK.name())) dcl.rollback(con);
            else if (kind.equalsIgnoreCase(DBType.DDL.ALTER.name())) ddl.alter(sql, con); //DDL
            else if (kind.equalsIgnoreCase(DBType.DDL.DROP.name())) ddl.drop(sql, con);
            else if (kind.equalsIgnoreCase(DBType.DDL.CREATE.name())) ddl.create(sql, con);
            else if (kind.equalsIgnoreCase(DBType.DML.DELETE.name())) dml.delete(con, sql); //DML
            else if (kind.equalsIgnoreCase(DBType.DML.UPDATE.name())) dml.update(con, sql);
            else if (kind.equalsIgnoreCase(DBType.DML.INSERT.name())) dml.insert(con, sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.printf("%s디비 생성에 실패하였습니다.%s\n", Color.RED, Color.RESET);
        }
    }

    //select 문 [SELECT * FROM TEST][Value]
    //return = [ㅁㄴㅇㄹ, ㅁㄹㄴㅇ, ㅁㄹㅇㄴ]
    public String getSelectValue(String sql) {
        sql = sql.trim();
        String[] sqlValue = sql.substring(1, sql.length()-1).split("]\\[");
        if (sqlValue.length != 2) throw new MatchException(MatchMessage.grammarError);
        try (var con = DriverManager.getConnection(dbUrl)) {
            return dml.select(con, sqlValue[0], sqlValue[1]).toString();
        } catch (SQLException e) {
            return sql;
        }
    }

    public String getSelectValue(String sql, String value) {
        try (var con = DriverManager.getConnection(dbUrl)) {
            return dml.select(con, sql, value).toString();
        } catch (SQLException e) {
            return sql;
        }
    }
}
