package etc.v3.compare_test;

import java.util.Arrays;
import java.util.StringTokenizer;

public class TrimVsStrip {
    public static void main(String[] args) {
        int count = 2000000000;
        String text = "        1234     12345  ";

        // trim
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) text = text.trim();

        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();

        // strip
        for (int i = 0; i < count; i++) text = text.strip();
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();

        // strip
        for (int i = 0; i < count; i++) text = text.stripLeading();
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();

        for (int i = 0; i < count; i++) text = text;
        System.out.println(System.currentTimeMillis() - start);

        /*
         * 1차 |
         * 6   |
         * 4   |
         * 1   |
         */
    }
}
