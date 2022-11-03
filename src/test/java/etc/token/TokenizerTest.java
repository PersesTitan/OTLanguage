package etc.token;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bin.token.VariableToken.VARIABLE;

public class TokenizerTest {
    public static void main(String[] args) {
        StringTokenizer tokenizer = new StringTokenizer("asdf a21sdf a1sdf");
        System.out.println(tokenizer.countTokens());
    }
}
