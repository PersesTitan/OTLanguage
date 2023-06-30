package bin.apply.item;

import bin.token.EditToken;
import bin.token.Token;

import java.util.Arrays;
import java.util.stream.Collectors;

public interface Item {
    default String itemToString(String klass, Object... values) {
        return klass + Token.PARAM_S +
                Arrays.stream(values).map(EditToken::toString).collect(Collectors.joining(Token.PARAM)) +
                Token.PARAM_E;
    }
}
