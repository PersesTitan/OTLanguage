package input.conroller;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class KeyboardController {
    public static Robot robot;

    public KeyboardController() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            robot = null;
        }
    }

    public boolean check(String line) {
        if (robot == null) return false;
        return false;
    }

    public void start() {

    }

    // (ex) Shift A, Ctrl C
    protected void checkButton(String...keys) {
        if (robot == null) {
            System.out.println("No have Keys");
        }
        List<Integer> list = new ArrayList<>();
        for (String key : keys) {
            key = key.trim().toUpperCase(Locale.ROOT);
            if (!KeyboardRepository.keys.containsKey(key)) continue;
            list.add(KeyboardRepository.keys.get(key));
        }

        Arrays.stream(keys)
                .map(String::trim)
                .map(String::toUpperCase)
                .filter(KeyboardRepository.keys::containsKey)
                .map(KeyboardRepository.keys::get)
                .forEach(robot::keyPress);

        list.forEach(System.out::println);
//        list.forEach(robot::keyPress);
//        list.forEach(robot::keyRelease);
    }
}
