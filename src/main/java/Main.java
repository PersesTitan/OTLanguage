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

        if (!new File(args[0]).canRead()) throw new Exception("파일을 읽을 수 없습니다.");
        if (args.length <= 0) throw new Exception("파일이 존재하지 않습니다.");
        if (!args[0].toLowerCase(Locale.ROOT).endsWith(".otl")) throw new Exception("확장자를 확인해주세요.");
        int count = 0;
        String text;
        varClear();
        System.out.println("================출력================");
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0], StandardCharsets.UTF_8))) {
            while ((text = reader.readLine()) != null) {
                idLine.put(count, text+" ");
                start(text+" ");
                count++;
            }
        }
        pause();
    }

    /**
     * 변수 감시시에 변수 변경 <br>
     * scanner 감지하였을때 입력 받음 <br>
     * @param line 라인 받아오기
     * @throws Exception 에러 났을때 에러 발생
     */
    private static void start(String line) throws Exception {
        if (!line.isBlank()) {
            if (variable.check(line)) line = variable.getVar(line);
            if (scannerP.check(line)) line = scannerP.start(line);
            if (ifp.check(line)) ifp.start(line);

            if (print.check(line)) print.start(line);
            else if (println.check(line)) println.start(line);
            else if (booleanP.check(line)) booleanP.start(line.strip());
            else if (characterP.check(line)) characterP.start(line.strip());
            else if (doubleP.check(line)) doubleP.start(line.strip());
            else if (floatP.check(line)) floatP.start(line.strip());
            else if (integerP.check(line)) integerP.start(line.strip());
            else if (longP.check(line)) longP.start(line.strip());
            else if (stringP.check(line)) stringP.start(line.strip());
        }
    }

    private static void pause() {
        try {
            System.out.println("\n=================================");
            System.out.println("종료 : Enter");
            System.in.read();
        } catch (Exception ignored) {}
    }
}
