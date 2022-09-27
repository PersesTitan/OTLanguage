import org.jcodings.specific.UTF16LEEncoding;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        File file = new File("hello.otl");
        String path1 = file.getPath();
        String path2 = file.getAbsolutePath();
        String path3 = file.getAbsoluteFile().getParent();
        String path4 = file.getParent();

        System.out.println(path1);
        System.out.println(path2);
        System.out.println(path3);


        System.out.println(Arrays.toString("⌄".getBytes(StandardCharsets.UTF_16)));
        System.out.println(Arrays.toString("a".getBytes(StandardCharsets.UTF_16)));
        System.out.println(Arrays.toString("ㅇㅅㅇ".getBytes(StandardCharsets.UTF_16)));
        System.out.println(Arrays.toString("한".getBytes(StandardCharsets.UTF_16)));

        byte[][] bs = new byte[100][4];
        for (int i = 0; i<100; i++) {
            String[] value = Integer.toString(i).split("");
            bs[i][0] = -2;
            bs[i][1] = -1;
            bs[i][2] = Byte.parseByte(value[0]);
            bs[i][3] = Byte.parseByte(value.length > 1 ? value[1] : "0");
        }

        for (int i = 0; i<bs.length; i++) {
            System.out.print(new String(bs[i], StandardCharsets.UTF_16));
            System.out.print(" ");
            if (i%5 == 4) System.out.println();
        }


//        for (int i = 0; i<bs.length; i++) {
//            bs[i] = {};
//        }

//        System.out.println(new String(b, StandardCharsets.UTF_16));
//        Unicode.list.forEach(System.out::println);
    }
}
