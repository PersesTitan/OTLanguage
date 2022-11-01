package cos.mouse.controller;

import bin.token.LoopToken;
import work.ReturnWork;
import work.v3.ReturnWorkV3;

import java.awt.*;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetMouseX extends ReturnWorkV3 implements LoopToken {
    // 0
    public GetMouseX(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        return String.valueOf(MouseInfo.getPointerInfo().getLocation().x);
    }
}
