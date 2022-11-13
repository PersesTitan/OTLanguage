package etc.input.keyboard;

import etc.input.keyboard.exception.KeyboardException;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static java.awt.event.KeyEvent.*;

public class KeyboardTest {
    public static void main(String[] args) {
        new KeyboardTest();
    }

    public KeyboardTest() {
        CreateIconTest.setIcon();
        System.setProperty("java.awt.headless", "false");
        try {
            final Robot robot = new Robot();
            robot.delay(1000);
            robot.keyPress(VK_0);
            robot.delay(1);
            robot.keyRelease(VK_0);
        } catch (AWTException e) {
            throw new KeyboardException().awtError();
        } catch (SecurityException e) {
            throw new KeyboardException().securityException();
        }
    }

    private final Map<String, Integer> keyboard = new HashMap<>() {{
        put("ENTER", VK_ENTER);
        put("BACK_SPACE", VK_BACK_SPACE);
        put("TAB", VK_TAB);
        put("SHIFT", VK_SHIFT);
        put("CONTROL", VK_CONTROL);
        put("ALT", VK_ALT);
        put("CAPS_LOCK", VK_CAPS_LOCK);

        put("ESCAPE", VK_ESCAPE);
        put("SPACE", VK_SPACE);
        put("PAGE_UP", VK_PAGE_UP);
        put("PAGE_DOWN", VK_PAGE_DOWN);
        put("END", VK_END); put("HOME", VK_HOME);
        put("LEFT", VK_LEFT); put("UP", VK_UP); put("RIGHT", VK_RIGHT); put("DOWN", VK_DOWN);
        put(",", VK_COMMA); put("-", VK_MINUS); put(".", VK_PERIOD); put("/", VK_SLASH);

        put("0", VK_0); put("1", VK_1); put("2", VK_2); put("3", VK_3); put("4", VK_4);
        put("5", VK_5); put("6", VK_6); put("7", VK_7); put("8", VK_8); put("9", VK_9);

        put("A", VK_A); put("B", VK_B); put("C", VK_C); put("D", VK_D); put("E", VK_E); put("F", VK_F); put("G", VK_G);
        put("H", VK_H); put("I", VK_I); put("J", VK_J); put("K", VK_K); put("L", VK_L); put("M", VK_M); put("N", VK_N);
        put("O", VK_O); put("P", VK_P); put("Q", VK_Q); put("R", VK_R); put("S", VK_S); put("T", VK_T); put("U", VK_U);
        put("V", VK_V); put("W", VK_W); put("X", VK_X); put("Y", VK_Y); put("Z", VK_Z);
    }};
}
