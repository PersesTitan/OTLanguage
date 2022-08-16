package origin.loop.model;

import event.Setting;
import origin.loop.controller.If;

import java.util.regex.Pattern;

public interface LoopWork {
    boolean check(String line);
    void start(String line);
    String getPattern();

    default boolean breakCheck(String line) {
        return Pattern.compile("^\\s*ㅂㅇㅂ\\s*$").matcher(line).find();
    }

    default boolean continueCheck(String line) {
        return Pattern.compile("^\\s*ㅋㅇㅋ\\s*$").matcher(line).find();
    }

    //break, continue 확인 하고 출력
    //실행까지 동작함
    default BreakContinue settingStart(String line) {
        line = Setting.startString(line);
        for (String l : If.lineStart(line).split("\\n")) {
            if (breakCheck(line)) return BreakContinue.Break;
            if (continueCheck(line)) return BreakContinue.Continue;
            Setting.start(l);
        }
        return BreakContinue.Etc;
    }

    enum BreakContinue {
        Break, Continue, Etc
    }
}
