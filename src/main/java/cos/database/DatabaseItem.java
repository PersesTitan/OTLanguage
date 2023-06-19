package cos.database;

import bin.apply.Repository;
import bin.apply.item.Item;
import bin.token.KlassToken;
import bin.token.Token;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DatabaseItem implements Item {
    private final Connection conn;

    public DatabaseItem(String url, String user, String password) {
        try {
            this.conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw DatabaseException.CREATE_ERROR.getThrow(e.getMessage());
        }
    }

    public DatabaseItem(String url) {
        try {
            this.conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw DatabaseException.CREATE_ERROR.getThrow(e.getMessage());
        }
    }

    // DDL (create, alter, drop, truncate)
    // DCL (commit, rollback, grant, revoke)
    public void sql(String sql) {
        try {
            this.getSQL(sql).execute();
        } catch (SQLException e) {
            throw DatabaseException.SQL_RUN_ERROR.getThrow(sql);
        }
    }

    // DML (select, insert, update, delete)
    ResultSet select(String sql) {
        try {
            return this.getSQL(sql).executeQuery();
        } catch (SQLException e) {
            throw DatabaseException.SQL_ERROR.getThrow(sql);
        }
    }

    private final static Matcher SQL_MATCHER = Pattern
            .compile(String.format("%c(%c*%s)", Token.PUT, Token.ACCESS, Token.VARIABLE))
            .matcher("");
    private PreparedStatement getSQL(String sql) {
        try {
            List<String> list = new ArrayList<>();
            PreparedStatement stat = conn.prepareStatement(SQL_MATCHER.reset(sql).replaceAll(mr -> {
                list.add(mr.group(1));
                return "?";
            }));
            ListIterator<String> iterator = list.listIterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = Repository.repositoryArray.getEntry(iterator.next());
                int i = iterator.nextIndex();
                Object value = entry.getValue();
                switch (entry.getKey()) {
                    case KlassToken.INT_VARIABLE -> stat.setInt(i, (int) value);
                    case KlassToken.LONG_VARIABLE -> stat.setLong(i, (long) value);
                    case KlassToken.FLOAT_VARIABLE -> stat.setFloat(i, (float) value);
                    case KlassToken.DOUBLE_VARIABLE -> stat.setDouble(i, (double) value);
                    case KlassToken.BOOL_VARIABLE -> stat.setBoolean(i, (boolean) value);
                    case KlassToken.STRING_VARIABLE -> stat.setString(i, (String) value);
                    default -> stat.setObject(i, value);
                }
            }
            return stat;
        } catch (SQLException e) {
            throw DatabaseException.SQL_ERROR.getThrow(sql);
        }
    }

    @Override
    public String toString() {
        return itemToString(DatabaseToken.DATABASE);
    }
}
