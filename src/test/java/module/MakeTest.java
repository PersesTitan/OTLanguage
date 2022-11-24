package module;

import bin.apply.Setting;
import bin.calculator.tool.CalculatorTool;
import bin.exception.MatchException;
import work.v3.ReturnWorkV3;
import work.v3.StartWorkV3;

import java.io.File;
import java.util.*;

import static bin.apply.Repository.*;
import static bin.apply.sys.item.Separator.*;
import static bin.calculator.tool.CalculatorTool.*;
import static bin.token.Token.NO_TOKEN;
import static bin.token.Token.TOKEN;
import static bin.token.cal.BoolToken.*;

public class MakeTest {
    public static void main(String[] args) {
        String a = "asd.af.otl";
        System.out.println(a.substring(0, a.lastIndexOf('.')));
    }
}
