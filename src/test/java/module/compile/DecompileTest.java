package module.compile;

import bin.apply.Repository;
import bin.apply.Setting;
import bin.apply.sys.compile.Decompile;
import bin.apply.sys.item.SystemSetting;
import bin.apply.sys.make.StartLine;
import bin.exception.FileException;
import module.compile.item.FileSave;

import java.io.*;
import java.util.Locale;

public class DecompileTest {
    ///////////////////////////
    ////////// 테스트 ///////////
    ///////////////////////////
    public static void main(String[] args) {
        new Decompile(new File("web.otlc"));
    }
}
