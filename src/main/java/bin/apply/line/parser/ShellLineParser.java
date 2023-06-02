package bin.apply.line.parser;

import bin.apply.item.ShellCodeItem;
import bin.apply.line.LineTool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ShellLineParser extends LineParser {
    private final ArrayList<String> FILES;
    private final ArrayList<LineTool> LINES;

    public ShellLineParser(Map<Integer, String> codes, ShellCodeItem CODE, int line) {
        super(codes, CODE);
        final int SIZE = codes.size();
        super.start = line;
        this.LINES = CODE.getLINES();
        this.FILES = CODE.getFILES();
        LINES.addAll(Arrays.asList(new LineTool[SIZE-start]));
        for (; start < SIZE; start++) LINES.set(start, this.parser(SYSTEM, start));
    }

    @Override
    protected LineTool loopPut(String REPO_KLASS, int s, int e, LineTool tool) {
        try {
            return tool;
        } finally {
            LINES.set(e, null);
            super.start = e;
            for (int i = s+1; i < e; i++) LINES.set(i, parser(REPO_KLASS, i));
        }
    }

    @Override
    public LineTool[] getLINES() {
        return LINES.toArray(LineTool[]::new);
    }

    @Override
    public String[] getFILES() {
        return FILES.toArray(String[]::new);
    }
}
