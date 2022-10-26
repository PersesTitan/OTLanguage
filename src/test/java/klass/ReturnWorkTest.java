package klass;

import bin.exception.MatchException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.IntStream;

public abstract class ReturnWorkTest implements Serializable {
    private final int[] counts;

    protected ReturnWorkTest(int...counts) {
        this.counts = counts.length == 0 ? null : counts;
    }

    public abstract String start(String line, String[] params, LinkedList<Map<String, Map<String, Object>>> repositoryArray);

    public ReturnWorkTest paramsCheck(int size, String params) {
        if (!(counts == null
                || (params != null && counts.length == 1 && counts[0] == 0 && params.isEmpty())
                || Arrays.stream(counts).anyMatch(v -> v == size))) throw MatchException.grammarError();
        return this;
    }
}
