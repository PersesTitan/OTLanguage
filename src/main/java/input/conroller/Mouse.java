package input.conroller;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

public class Mouse {

    public static void main(String[] args) {

        KeyboardController controller = new KeyboardController();
        controller.checkButton("a", "ag", "ag");

        System.out.println(MouseInfo.getPointerInfo().getLocation());
//        while (true) {
//
//            System.out.println(KeyEvent.getKeyText(KeyEvent.VK_A));
//            try {
//                Thread.sleep(1000);
//            } catch (Exception ignored) {}
//        }

//        PointerInfo info = MouseInfo.getPointerInfo();
//        try {
//            Robot robot = new Robot();
//
//        } catch (AWTException e) {
//            e.printStackTrace();
//        }
//
//        while (true) {
//            info = MouseInfo.getPointerInfo();
//            System.out.println(info.getLocation().getX());
//            System.out.println(info.getLocation().getY());
//
//            try {
//                Thread.sleep(1000);
//            } catch (Exception ignored) {}
//        }
    }
}
