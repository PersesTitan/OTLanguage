package bin.apply;

import bin.apply.sys.make.StartLine;
import bin.calculator.number.AllNumberCalculator;
import bin.calculator.bool.all.AllBoolCalculator;
import bin.calculator.bool.all.AllCompareCalculator;
import bin.check.VariableTypeCheck;
import bin.exception.ConsoleException;
import bin.exception.FileException;
import bin.orign.GetSetVariable;
import bin.orign.loop.For;
import bin.string.list.AggregationList;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static bin.apply.Setting.debugMode;
import static bin.apply.sys.item.Separator.SEPARATOR_LINE;
import static bin.token.Token.REMARK;

public interface Controller {
    AllNumberCalculator allNumberCalculator = new AllNumberCalculator();
    AllBoolCalculator allBoolCalculator = new AllBoolCalculator();
    AllCompareCalculator allCompareCalculator = new AllCompareCalculator();

    GetSetVariable getSetVariable = new GetSetVariable();

    AggregationList aggregationList = new AggregationList();
    // 입력, 출력
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static String scanner() {
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new ConsoleException().scannerError();
        }
    }

    static void print(String text) {
        try {
            bw.write(text);
            bw.flush();
        } catch (IOException e) {
            throw new ConsoleException().printError();
        }
    }

    static void println(String text) {
        try {
            bw.write(text);
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            throw new ConsoleException().printError();
        }
    }

    // 프로그램 종료 방지
    static void allLoop() {
        while (true) {}
    }

    static void readFile(String path, StringBuilder builder) {
        readFile(new File(path), builder);
    }

    static void readFile(File file, StringBuilder builder) {
        try (FileReader fileReader = new FileReader(file, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(fileReader)) {
            for (int i = 1; ; i++) {
                String line = reader.readLine();
                if (line == null) break;
                line = line.stripLeading();
                builder.append(i)
                        .append(" ")
                        .append(line.startsWith(REMARK) ? "" : line)
                        .append(SEPARATOR_LINE);
            }
        } catch (IOException e) {
            if (StartLine.developmentMode) e.printStackTrace();
            throw new FileException().noReadError();
        }
    }
}
