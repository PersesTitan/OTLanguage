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
        int hangle_start = '\uAC00';
        int hangle_end = '\uD7A3' + 1;
        Random random = new Random();
        System.out.println(hangle_start);
        System.out.println(hangle_end);
        System.out.println((char) random.nextInt(hangle_start, hangle_end));
        System.out.println((char) random.nextInt(hangle_start, hangle_end));
        System.out.println((char) random.nextInt(hangle_start, hangle_end));
        System.out.println((char) random.nextInt(hangle_start, hangle_end));
        System.out.println(UUID.randomUUID());
        System.out.println(UUID.randomUUID());
        System.out.println(UUID.randomUUID());
        System.out.println(UUID.randomUUID());
        System.out.println(UUID.randomUUID());
    }

    private final static int START_VALUE = 44032;
    private final static int END_VALUE = 55204;
    private static String getRandom() {

    }
}
