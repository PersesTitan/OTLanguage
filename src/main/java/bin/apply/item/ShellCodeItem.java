package bin.apply.item;

import bin.apply.line.LineTool;
import bin.exception.Error;
import lombok.Getter;

import java.io.Serial;
import java.util.ArrayList;

@Getter
public class ShellCodeItem extends CodeItem {
    @Serial
    private static final long serialVersionUID = -3558381618248209043L;
    private final ArrayList<String> FILES = new ArrayList<>();
    private final ArrayList<LineTool> LINES = new ArrayList<>();

    public ShellCodeItem() {
        super("", null, null);
    }

    @Override
    public void start() {
        start(0, LINES.size());
    }

    @Override
    public void start(int s, int e) {
        for (int i = s; i < e;) {
            try {
                i = start(i);
            } catch (Error error) {
                error.print(super.PATH, FILES.get(i), i+1);
                throw error;
            }
        }
    }

    @Override
    public void startTry(int s, int e) {
        for (int i = s; i < e;) i = start(i);
    }

    @Override
    protected int start(int i) {
        return LINES.get(i) == null ? i+1 : LINES.get(i).start(i);
    }

    public void add(LineTool lines, String files) {
        this.LINES.add(lines);
        this.FILES.add(files);
    }

    public void add(String line) {
        FILES.add(line);
    }
}
