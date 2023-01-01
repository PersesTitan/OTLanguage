package etc.db.item.db;

import lombok.Getter;

// 제약 조건
@Getter
public enum DBOption {
    NOT_NULL("NOT NULL"),
    UNIQUE("UNIQUE"),
    PRIMARY_KEY("PRIMARY KEY"),
    // @TODO change query
    FOREGIN_KEY("FOREGIN KEY"),
    // @TODO change query
    CHECK("CHECK"),
    // @TODO change query
    INDEX("INDEX"),
    // @TODO change query
    DEFAULT("DEFAULT");

    private final String query;
    DBOption(String query) {
        this.query = query;
    }
}
