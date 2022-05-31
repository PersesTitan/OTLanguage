package item;

import Calculation.Account;
import Variable.*;
import etc.TryCatch;
import etc.TryCatchPrint;
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

    TryCatch tryCatch = new TryCatch();
    TryCatchPrint tryCatchPrint = new TryCatchPrint();

    Account account = new Account();
    If ifp = new If();
}
