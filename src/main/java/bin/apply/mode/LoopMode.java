package bin.apply.mode;

import bin.exception.MatchException;
import bin.token.check.CheckToken;
import bin.token.Token;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public enum LoopMode {
    NONE(v -> ""),
    PUT(line -> line.substring(line.indexOf(Token.PUT_TOKEN) + Token.PUT_TOKEN.length()).strip()),
    RETURN(line -> line.substring(line.indexOf(Token.RETURN_TOKEN) + Token.RETURN_TOKEN.length()).strip());

    private final Function<String, String> function;

    public static LoopMode getLoop(String line) {
        if (CheckToken.startWith(line, Token.LOOP_E)) {
            if (line.length() == 1) return NONE;
            int i = line.indexOf(Token.PUT_TOKEN);
            if (i != -1 && line.substring(1, i).isBlank()
                    && !line.substring(i + Token.PUT_TOKEN.length()).isBlank()) return PUT;
            i = line.indexOf(Token.RETURN_TOKEN);
            if (i != -1 && line.substring(1, i).isBlank()
                    && !line.substring(i + Token.RETURN_TOKEN.length()).isBlank()) return RETURN;
        }
        throw MatchException.GRAMMAR_ERROR.getThrow(line);
    }

    public String cut(String line) {
        return this.function.apply(line);
    }
}
