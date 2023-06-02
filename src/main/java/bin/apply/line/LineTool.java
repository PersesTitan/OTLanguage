package bin.apply.line;

import java.io.Serializable;

public interface LineTool extends Serializable {
    void startItem();
    default int start(int line) {
        this.startItem();
        return line + 1;
    }
}
