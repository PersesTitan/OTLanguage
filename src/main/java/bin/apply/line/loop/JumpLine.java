package bin.apply.line.loop;

import bin.apply.line.LineTool;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JumpLine implements LineTool {
    private final int END;

    @Override
    public void startItem() {}

    @Override
    public int start(int line) {
        return END;
    }
}
