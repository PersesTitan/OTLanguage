package etc.input.keyboard.start;

import work.v3.StartWorkV3;

import java.awt.*;
import java.util.LinkedList;
import java.util.Map;

public class TypingTest extends StartWorkV3 {
    private final Robot robot;

    public TypingTest(Robot robot, int... counts) {
        super(counts);
        this.robot = robot;
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {

    }
}
