package etc.v3.compare_test;

import bin.token.StringToken;
import bin.token.Token;

import java.util.LinkedHashSet;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static bin.token.Token.COMMA;

public class StringTokenizerVsAsSplit {
    public static void main(String[] args) {

        StringTokenizer t = new StringTokenizer("ㅇㅇ,ㄴㄴ, ㅇㅇ   ,      ㅇ , ㅇㅇ", COMMA + " \t\n\r\f");
        while (t.hasMoreTokens()) System.out.println(t.nextToken());

        String line = "[ 1, 2, 3,4,5,61, 116,  2354 , 252,0]";



        int count = 1000000;
        long start = System.currentTimeMillis();
        for (int i = 0; i<count; i++) {
            LinkedHashSet<Integer> set = new LinkedHashSet<>();
            StringTokenizer tokenizer = new StringTokenizer(line.substring(1, line.length()-1), COMMA + " \t\n\r\f");
            while (tokenizer.hasMoreTokens()) set.add(Integer.parseInt(tokenizer.nextToken()));
        }

        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();

        for (int i = 0; i<count; i++) {
            Pattern.compile(COMMA)
                    .splitAsStream(line.substring(1, line.length()-1))
                    .map(String::strip)
                    .map(Integer::parseInt)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }

        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
        for (int i = 0; i<count; i++) {
            LinkedHashSet<Integer> set = new LinkedHashSet<>();
            StringTokenizer tokenizer = new StringTokenizer(line.substring(1, line.length()-1), COMMA);
            while (tokenizer.hasMoreTokens()) {
                set.add(Integer.parseInt(tokenizer.nextToken().strip()));
            }
        }
        System.out.println(System.currentTimeMillis() - start);

    }
}
