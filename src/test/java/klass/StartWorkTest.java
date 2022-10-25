package klass;

import bin.exception.MatchException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.IntStream;

public abstract class StartWorkTest {
    private final int[] counts;

    protected StartWorkTest(int...counts) {
        this.counts = counts.length == 0 ? null : counts;
    }

    abstract void start(String line, String[] params, LinkedList<Map<String, Map<String, Object>>> repositoryArray);
    public StartWorkTest paramsCheck(int size) {
        if (!(counts == null || Arrays.stream(counts).anyMatch(v -> v == size))) throw MatchException.grammarError();
        return this;
    }
}
