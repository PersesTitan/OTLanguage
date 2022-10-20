package token;

import java.util.StringTokenizer;

public class TokenizerTest {
    public static void main(String[] args) {
        StringTokenizer tokenizer = new StringTokenizer("asdf a21sdf a1sdf");
        System.out.println(tokenizer.countTokens());
    }
}
