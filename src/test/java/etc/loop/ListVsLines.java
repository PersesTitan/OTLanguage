package etc.loop;

import java.util.regex.Matcher;

import static bin.apply.sys.item.Separator.SEPARATOR_LINE;

public class ListVsLines {
    public static void main(String[] args) {
        String test = """
                aaa
                aaa
                aaa
                
                
                
                safdfs
                s
                fs
                f
                s
                fs
                df
                """;

        int count = 10000000;

        long s = System.currentTimeMillis();
        for (int i = 0; i<count; i++) {
            for (var a : test.lines().toList()) {

            }
        }
        System.out.println(System.currentTimeMillis() - s);

        s = System.currentTimeMillis();
        for (int i = 0; i<count; i++) {
            for (var a : test.split(SEPARATOR_LINE)) {

            }
        }
        System.out.println(System.currentTimeMillis() - s);
    }

    // lines.toList() < split
}
