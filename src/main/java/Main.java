import print.ScannerP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;


public class Main {
    static int count = 0;
    public static void main(String[] args) throws Exception {

        System.out.println(args[0]);
        pause();

        args = new String[1];
        args[0] = "./hello.OTL";

        if (args.length <= 0) throw new Exception("파일이 존재하지 않습니다.");
        if (!args[0].toLowerCase(Locale.ROOT).endsWith(".otl")) throw new Exception("확장자를 읽을 수 없습니다.");
//        BufferedReader reader = new BufferedReader(new FileReader(args[0], StandardCharsets.UTF_8));
        String readerString;

        count = 0;
        StringBuilder text;
        StringBuilder builder = new StringBuilder("");
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0], StandardCharsets.UTF_8))){
            while ((text = new StringBuilder(reader.readLine())) != null) {
                builder.append(text);
            }
        }
        pause();
    }

    private static void pause() throws IOException {
        try {
            System.out.println("=================================");
            System.out.println("종료 : Enter");
            System.in.read();
        } catch (Exception ignored) {}
    }
}
