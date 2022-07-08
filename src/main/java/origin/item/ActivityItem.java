package origin.item;

import http.variable.HttpWork;
import http.variable.PortVariable;
import http.variable.StartServer;
import http.variable.WebPageVariable;
import origin.calculation.Account;
import origin.calculation.Calculation;
import origin.comparison.Comparison;
import origin.comparison.ComparisonBool;
import origin.etc.TryCatch;
import origin.etc.TryCatchPrint;
import origin.item.work.ComparisonWork;
import origin.loop.Bracket;
import origin.loop.For;
import origin.print.*;
import origin.variable.*;
import origin.comparison.StringComparison;
import origin.loop.If;

public interface ActivityItem {
    String typeErrorMessage = "타입 오류가 발생하였습니다.";
    VarCheck varCheck = new VarCheck();

    ScannerP scannerP = new ScannerP();
    Print print = new Print();
    Println println = new Println();
    PriorityPrint priorityPrint = new PriorityPrint();
    PriorityPrintln priorityPrintln = new PriorityPrintln();

    BooleanP booleanP = new BooleanP();
    CharacterP characterP = new CharacterP();
    DoubleP doubleP = new DoubleP();
    FloatP floatP = new FloatP();
    IntegerP integerP = new IntegerP();
    LongP longP = new LongP();
    StringP stringP = new StringP();
    Variable variable = new Variable();
    SetVariable setVariable = new SetVariable();

    TryCatch tryCatch = new TryCatch();
    TryCatchPrint tryCatchPrint = new TryCatchPrint();

    Account account = new Account();
    If ifP = new If();
    For forP = new For();

    Calculation calculation = new Calculation();
    ComparisonWork comparison = new Comparison();
    ComparisonWork comparisonBool = new ComparisonBool();
    ComparisonWork stringComparison = new StringComparison();

    //시작할때 실행
    Bracket bracket = new Bracket();

    //웹 플러그
    HttpWork portVariable = new PortVariable();
    HttpWork startServer = new StartServer();
    HttpWork webPageVariable = new WebPageVariable();
}
