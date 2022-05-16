import Calculation.Account;
import item.Setting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;


public class Main extends Setting {

    public static void main(String[] args) throws Exception {

//        args = new String[1];
//        args[0] = "hello.OTL";
//
//        if (args.length <= 0) throw new Exception("파일이 존재하지 않습니다.");
//        if (!args[0].toLowerCase(Locale.ROOT).endsWith(".otl")) throw new Exception("확장자를 읽을 수 없습니다.");
////        BufferedReader reader = new BufferedReader(new FileReader(args[0], StandardCharsets.UTF_8));
//        String readerString;
//
        int count = 0; String text;
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0], StandardCharsets.UTF_8))){
            while ((text = reader.readLine()) != null) {
                idLine.put(count, text);
                builder.append(text).append("\n");
                count++;
            }
        }

        String total = builder.toString();

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
