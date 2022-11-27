package etc.database.exception;

import bin.exception.CosException;

public class SQLException extends CosException {

    public SQLException() {}

    public SQLException(String message) {
        super(message);
    }

    public SQLException sqlError() {
        String message = """
                SQL문을 실행할 수 없습니다.
                Unable to execute SQL statement.
                Please check the SQL statement.
                """;
        return new SQLException(message);
    }
}
