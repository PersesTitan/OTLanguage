package item;

import calculation.Account;
import calculation.Calculation;
import variable.*;
import comparison.Comparison;
import comparison.ComparisonBool;
import comparison.StringComparison;
import etc.TryCatch;
import etc.TryCatchPrint;
import item.work.ComparisonWork;
import loop.Bracket;
import loop.For;
import loop.If;
import print.Print;
import print.Println;
import print.ScannerP;

public interface ActivityItem {
    String typeErrorMessage = "타입 오류가 발생하였습니다.";
    VarCheck varCheck = new VarCheck();

    ScannerP scannerP = new ScannerP();
    Print print = new Print();
    Println println = new Println();
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
}
