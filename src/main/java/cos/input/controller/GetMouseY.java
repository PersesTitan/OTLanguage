package cos.input.controller;

import bin.token.LoopToken;
import work.v3.ReturnWorkV3;

import java.awt.*;
import java.util.LinkedList;
import java.util.Map;

public class GetMouseY extends ReturnWorkV3 implements LoopToken {
    // 0
    public GetMouseY(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        return String.valueOf(MouseInfo.getPointerInfo().getLocation().y);
    }
}
