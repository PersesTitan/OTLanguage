package etc.db.exception;

import bin.exception.CosException;

public class SQLExceptionTest extends CosException {

    public SQLExceptionTest() {}

    public SQLExceptionTest(String message) {
        super(message);
    }

    public SQLExceptionTest sqlCreateError() {
        String message = """
                데이터베이스를 생성할 수 없습니다.
                Database could not be created.
                """;
        return new SQLExceptionTest(message);
    }

    public SQLExceptionTest sqlError() {
        String message = """
                SQL문을 실행할 수 없습니다.
                Unable to execute SQL statement.
                Please check the SQL statement.
                """;
        return new SQLExceptionTest(message);
    }

    public SQLExceptionTest updateError() {
        String message = """
                내용 변경이 없습니다.
                No content changes.
                Database value has not changed.
                """;
        return new SQLExceptionTest(message);
    }
}
