package bin.apply;

import bin.apply.sys.make.Bracket;
import bin.calculator.NumberCalculator;
import bin.calculator.bool.BoolCalculator;
import bin.calculator.bool.CompareCalculator;
import bin.exception.ConsoleException;
import bin.orign.loop.tool.LoopController;

import java.io.*;

public interface Controller {
    NumberCalculator numberCalculator = new NumberCalculator();
    BoolCalculator boolCalculator = new BoolCalculator();
    CompareCalculator compareCalculator = new CompareCalculator();
    LoopController loopController = new LoopController();
    Bracket bracket = new Bracket();
    // 입력, 출력
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static String scanner() {
        try {
            return br.readLine();
        } catch (IOException e) {
            throw ConsoleException.scannerError();
        }
    }

    static void print(String text) {
        try {
            bw.write(text);
            bw.flush();
        } catch (IOException e) {
            throw ConsoleException.printError();
        }
    }
}
