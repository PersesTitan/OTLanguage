package loop;

import item.work.LoopPosition;
import item.work.LoopWork;

import java.util.UUID;

public class Loop implements LoopWork {
    public void getLoop(String total) {
        while (check(total)) {
            LoopPosition loopPosition = countPattern(total);
            String start = total.substring(loopPosition.getStart());
            int end = endPattern.matcher(start).end();
            String random = " ".repeat(Math.max(0, loopPosition.getBlank())) +
                    UUID.randomUUID();
            String cut = total.substring(loopPosition.getStart(), end);
            total = total.replace(cut, random);
            getLoop(total);
        }
    }
}
