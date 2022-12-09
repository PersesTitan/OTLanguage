package module.compile;

import bin.apply.sys.compile.Compile;
import bin.apply.sys.item.DebugMode;
import bin.apply.sys.make.StartLine;
import module.compile.item.CountMap;
import module.compile.item.FileSave;
import module.compile.tool.FindModel;

import java.io.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static bin.apply.Repository.*;
import static bin.apply.Setting.debugMode;

public class CompileTest {

    public static void main(String[] args) {
//        new MakeGitTest();
//        new Setting();

        StartLine.developmentMode = true;
        new Compile(new File("web.otl"));
    }
}
