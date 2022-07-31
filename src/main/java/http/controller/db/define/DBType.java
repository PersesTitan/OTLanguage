package http.controller.db.define;

public class DBType {
    public enum DDL {
        CREATE, ALTER, DROP
    }

    public enum DCL {
        GRANT, REVOKE, COMMIT, ROLLBACK
    }

    public enum DML {
        SELECT, INSERT, UPDATE, DELETE
    }
}
