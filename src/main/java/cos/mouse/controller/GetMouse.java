package cos.mouse.controller;

import bin.token.LoopToken;
import work.v3.ReturnWorkV3;

import java.awt.*;
import java.util.LinkedList;
import java.util.Map;

public class GetMouse extends ReturnWorkV3 implements LoopToken {
    // 0
    public GetMouse(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        Point local = MouseInfo.getPointerInfo().getLocation();
        return String.format("[%d, %d]", local.x, local.y);
    }
}
