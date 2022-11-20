package etc.database.item;

public enum SQL {
    CREATE, ALTER, DROP, TRUNCATE,  // DCL
    SELECT, INSERT, UPDATE, DELETE, // DML
    COMMIT, ROLLBACK, GRANT, REVOKE // DCL
}
