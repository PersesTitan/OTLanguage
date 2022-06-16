import item.ActivityItem;
import item.Setting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class Main extends Setting implements ActivityItem {
    public static void main(String[] args) throws Exception {
        args = new String[1];
        args[0] = "./hello.otl";
        new Main(args);
    }

    Main(String[] args) throws Exception {
        if (args.length <= 0) throw new Exception("파일이 존재하지 않습니다.");
        if (!new File(args[0]).canRead()) throw new Exception("파일을 읽을 수 없습니다.");
        if (!args[0].toLowerCase(Locale.ROOT).endsWith(".otl")) throw new Exception("확장자를 확인해주세요.");
        int count = 0;
        varClear();
        String text;
        System.out.println("================출력================");
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0], StandardCharsets.UTF_8))) {
            while ((text = reader.readLine()) != null) {
                idLine.put(count, text);
                total.append(text);
                start(text);
                count++;
            }
        }
    }
}
