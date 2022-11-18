package cos.input;

import cos.input.exception.KeyboardException;
import cos.input.setting.SetIcon;
import work.v3.StartWorkV3;

import java.awt.*;
import java.util.LinkedList;
import java.util.Map;

public class CreateRobot extends StartWorkV3 implements SetIcon {
    // 0
    public CreateRobot(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        System.setProperty("java.awt.headless", "false");
        setIcon("icon.otlm");       // 아이콘
        try {
            final Robot robot = new Robot();
        } catch (AWTException e) {
            throw new KeyboardException().awtError();
        } catch (SecurityException e) {
            throw new KeyboardException().securityException();
        }
    }
}
