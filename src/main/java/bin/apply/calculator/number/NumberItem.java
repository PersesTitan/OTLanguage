package bin.apply.calculator.number;

import bin.exception.MatchException;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
enum NumberItem {
    SUM(String.valueOf(NumberToken.ADD)),
    SUB(String.valueOf(NumberToken.SUB)),
    MUL(String.valueOf(NumberToken.MUL)),
    DIV(String.valueOf(NumberToken.DIV)),
    REM(String.valueOf(NumberToken.REM));

    private final String SING;

    public boolean is() {
        return switch (this) {
            case MUL, DIV, REM -> true;
            case SUM, SUB -> false;
        };
    }

    public static NumberItem get(String sing, String line) {
        for (NumberItem item : NumberItem.values()) {
            if (Objects.equals(item.SING, sing)) return item;
        }
        throw MatchException.NUMBER_GRAMMAR_ERROR.getThrow(line);
    }
}
