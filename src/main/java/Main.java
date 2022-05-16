import Variable.Variable;
import item.Setting;
import print.Print;
import print.Println;
import print.ScannerP;
import Variable.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;


public class Main extends Setting {

    public static void main(String[] args) throws Exception {

        args = new String[1];
        args[0] = "./hello.otl";

        if (!new File(args[0]).canRead()) throw new Exception("파일을 읽을 수 없습니다.");
        if (args.length <= 0) throw new Exception("파일이 존재하지 않습니다.");
        if (!args[0].toLowerCase(Locale.ROOT).endsWith(".otl")) throw new Exception("확장자를 확인해주세요.");
        int count = 0; String text;
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0], StandardCharsets.UTF_8))) {
            while ((text = reader.readLine()) != null) {
                idLine.put(count, text);
                builder.append(text).append("\n");
                count++;
            }
        }


        String total = builder.toString();

        pause();
    }

    private static void start(String line) throws Exception {
        final ScannerP scannerP = new ScannerP();
        final Print print = new Print();
        final Println println = new Println();
        final BooleanP booleanP = new BooleanP();
        final CharacterP characterP = new CharacterP();
        final DoubleP doubleP = new DoubleP();
        final FloatP floatP = new FloatP();
        final IntegerP integerP = new IntegerP();
        final LongP longP = new LongP();
        final StringP stringP = new StringP();

        if (scannerP.check(line)) scannerP.start(line);
        else if (print.check(line)) print.start(line);
        else if (println.check(line)) println.start(line);
        else if (booleanP.check(line)) booleanP.start(line);
        else if (characterP.check(line)) characterP.start(line);
        else if (doubleP.check(line)) doubleP.check(line);
        else if (floatP.check(line)) floatP.check(line);
        else if (integerP.check(line)) integerP.start(line);
        else if (longP.check(line)) longP.start(line);
        else if (stringP.check(line)) stringP.check(line);
    }

    private static void pause() {
        try {
            System.out.println("=================================");
            System.out.println("종료 : Enter");
            System.in.read();
        } catch (Exception ignored) {}
    }
}
