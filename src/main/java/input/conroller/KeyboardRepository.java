package input.conroller;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public interface KeyboardRepository {
    Map<String, Integer> keys = new HashMap<>() {{
        put("ENTER", KeyEvent.VK_ENTER);
        put("0", KeyEvent.VK_0);
        put("1", KeyEvent.VK_1);
        put("2", KeyEvent.VK_2);
        put("3", KeyEvent.VK_3);
        put("4", KeyEvent.VK_4);
        put("5", KeyEvent.VK_5);
        put("6", KeyEvent.VK_6);
        put("7", KeyEvent.VK_7);
        put("8", KeyEvent.VK_8);
        put("9", KeyEvent.VK_9);
        put("A", KeyEvent.VK_A);
        put("B", KeyEvent.VK_B);
        put("C", KeyEvent.VK_C);
        put("D", KeyEvent.VK_D);
        put("E", KeyEvent.VK_E);
        put("F", KeyEvent.VK_F);
        put("G", KeyEvent.VK_G);
        put("H", KeyEvent.VK_H);
        put("I", KeyEvent.VK_I);
        put("J", KeyEvent.VK_J);
        put("K", KeyEvent.VK_K);
        put("L", KeyEvent.VK_L);
        put("M", KeyEvent.VK_M);
        put("N", KeyEvent.VK_N);

        put("F1", KeyEvent.VK_F1);
        put("F2", KeyEvent.VK_F2);
        put("F3", KeyEvent.VK_F3);
        put("F4", KeyEvent.VK_F4);
        put("F5", KeyEvent.VK_F5);
        put("F6", KeyEvent.VK_F6);
        put("F7", KeyEvent.VK_F7);
        put("F8", KeyEvent.VK_F8);
        put("F9", KeyEvent.VK_F9);
        put("F10", KeyEvent.VK_F10);
        put("F11", KeyEvent.VK_F11);
        put("F12", KeyEvent.VK_F12);
        put("F13", KeyEvent.VK_F13);
        put("F14", KeyEvent.VK_F14);
        put("F15", KeyEvent.VK_F15);
        put("F16", KeyEvent.VK_F16);
        put("F17", KeyEvent.VK_F17);
        put("F18", KeyEvent.VK_F18);
        put("F19", KeyEvent.VK_F19);
        put("F20", KeyEvent.VK_F20);
        put("F21", KeyEvent.VK_F21);
        put("F22", KeyEvent.VK_F22);
        put("F23", KeyEvent.VK_F23);
        put("F24", KeyEvent.VK_F24);


    }};
}
