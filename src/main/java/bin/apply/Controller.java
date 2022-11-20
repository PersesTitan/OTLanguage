package bin.apply;

import bin.apply.sys.make.Bracket;
import bin.calculator.number.AllNumberCalculator;
import bin.calculator.bool.all.AllBoolCalculator;
import bin.calculator.bool.all.AllCompareCalculator;
import bin.check.VariableTypeCheck;
import bin.exception.ConsoleException;
import bin.orign.GetSetVariable;
import bin.orign.loop.For;
import bin.orign.loop.tool.LoopController;
import bin.string.list.AggregationList;

import java.io.*;

public interface Controller {
    AllNumberCalculator allNumberCalculator = new AllNumberCalculator();
    AllBoolCalculator allBoolCalculator = new AllBoolCalculator();
    AllCompareCalculator allCompareCalculator = new AllCompareCalculator();

    LoopController loopController = new LoopController();
    Bracket bracket = new Bracket();
    GetSetVariable getSetVariable = new GetSetVariable();
    VariableTypeCheck variableTypeCheck = new VariableTypeCheck();
    For forStart = new For();

    AggregationList aggregationList = new AggregationList();
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
