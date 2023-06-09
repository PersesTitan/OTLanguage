package bin.token.work;

import bin.apply.Repository;
import bin.apply.repository.method.KlassMap;
import bin.apply.work.system.*;

import static bin.token.KlassToken.SYSTEM;
import static work.ResetWork.*;

public interface SystemToken {
    String PRINTLN = "ㅆㅁㅆ";
    String PRINT = "ㅅㅁㅅ";
    String PRINT_TAB = "ㅅㅁㅆ";
    String PRINT_SPACE = "ㅆㅁㅅ";
    String QUIT = "ㄲㅌㄲ";
    String FIND = "ㅂㅅㅂ";

    String SCANNER_I = ">ㅅㅈㅅ";
    String SCANNER_L = ">ㅅㅉㅅ";
    String SCANNER_B = ">ㅅㅂㅅ";
    String SCANNER_S = ">ㅅㅁㅅ";
    String SCANNER_C = ">ㅅㄱㅅ";
    String SCANNER_F = ">ㅅㅅㅅ";
    String SCANNER_D = ">ㅅㅆㅅ";

    String EQUAL = "ㅇ=ㅇ";
    String TRY_CATCH = "ㅜㅅㅜ";
    String SLEEP = "=ㅅ=";
    String IF = "?ㅅ?";
    String WHILE = "$ㅅ$";
    String FOR_EACH = "$";
    String SHELL = "ㅆㄹㅆ";

    static void reset(KlassMap work) {
        work.addStatic(SYSTEM, SCANNER_I, ScannerItem::nextInt, i);
        work.addStatic(SYSTEM, SCANNER_L, ScannerItem::nextLong, l);
        work.addStatic(SYSTEM, SCANNER_B, ScannerItem::nextFloat, f);
        work.addStatic(SYSTEM, SCANNER_S, ScannerItem::nextDouble, d);
        work.addStatic(SYSTEM, SCANNER_C, ScannerItem::nextBool, b);
        work.addStatic(SYSTEM, SCANNER_F, ScannerItem::nextChar, c);
        work.addStatic(SYSTEM, SCANNER_D, ScannerItem::nextLine, s);
        work.addStatic(SYSTEM, QUIT, () -> System.exit(0));
        work.<Boolean, String>addStatic(SYSTEM, FIND, s, Repository.repositoryArray::find, b);
        work.put(SYSTEM, EQUAL, new Equal());
        work.put(SYSTEM, PRINTLN, new Println());
        work.put(SYSTEM, PRINT, new Print(""));
        work.put(SYSTEM, PRINT_TAB, new Print("\t"));
        work.put(SYSTEM, PRINT_SPACE, new Print(" "));
        work.put(SYSTEM, SLEEP, new Sleep());
        work.put(SYSTEM, SHELL, new Shell());
    }
}
