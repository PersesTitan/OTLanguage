package item;

import Calculation.Account;
import Variable.*;
import loop.If;
import print.Print;
import print.Println;
import print.ScannerP;

public interface ActivityItem {
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

    Account account = new Account();
    If ifp = new If();
}
