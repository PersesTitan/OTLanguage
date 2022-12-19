package work.v3;

import bin.exception.MatchException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public abstract class StartWorkV3 implements Serializable {
    private final int[] counts;

    public StartWorkV3(int...counts) {
        this.counts = counts.length == 0 ? null : counts;
    }

    public abstract void start(String line, String[] params, LinkedList<Map<String, Map<String, Object>>> repositoryArray);

    public StartWorkV3 paramsCheck(int size, String params) {
        if (counts == null) return this;
        else if (params != null && counts.length == 1 && counts[0] == 0 && params.isEmpty()) return this;
        else if (check(size)) return this;
        else throw new MatchException().grammarError();
    }

    private boolean check(int size) {
        for (int count : counts) if (count == size) return true;
        return false;
    }
}
