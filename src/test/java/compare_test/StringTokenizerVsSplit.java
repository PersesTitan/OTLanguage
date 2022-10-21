package compare_test;

import java.util.StringTokenizer;

public class StringTokenizerVsSplit {
    public static void main(String[] args) {
        String text = "1234 12345";

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            StringTokenizer tokenizer = new StringTokenizer(text);
            tokenizer.hasMoreTokens();
            tokenizer.nextToken();
            tokenizer.hasMoreTokens();
            tokenizer.nextToken();
        }
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();

        for (int i = 0; i < 100000; i++) {
            String[] texts = text.split("\\s+", 2);
            boolean b = texts.length != 2;
        }
        System.out.println(System.currentTimeMillis() - start);

        /* 1차   |  2차
         * 56   | 17917
         * 220  | 110829
         */
    }
}
