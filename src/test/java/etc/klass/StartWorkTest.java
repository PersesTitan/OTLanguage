package etc.klass;

import bin.exception.MatchException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public abstract class StartWorkTest implements Serializable {
    private final int[] counts;

    protected StartWorkTest(int...counts) {
        this.counts = counts.length == 0 ? null : counts;
    }

    abstract void start(String line, String[] params, LinkedList<Map<String, Map<String, Object>>> repositoryArray);
    public StartWorkTest paramsCheck(int size, String params) {
        if (!(counts == null
                || (params != null && counts.length == 1 && counts[0] == 0 && params.isEmpty())
                || Arrays.stream(counts).anyMatch(v -> v == size))) throw new MatchException().grammarError();
        return this;
    }
}
