package bin.apply.calculator.bool;

import bin.exception.MatchException;
import bin.token.Token;

enum BoolItem {
    OR, AND;

    public static BoolItem get(String sing, String line) {
        if (sing.length() != 1) throw MatchException.NUMBER_GRAMMAR_ERROR.getThrow(line);
        return switch (sing.charAt(0)) {
            case Token.OR -> OR;
            case Token.AND -> AND;
            default -> throw MatchException.NUMBER_GRAMMAR_ERROR.getThrow(line);
        };
    }
}
