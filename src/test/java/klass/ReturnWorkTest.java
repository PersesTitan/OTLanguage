package klass;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.IntStream;

public abstract class ReturnWorkTest {
    private final IntStream counts;

    protected ReturnWorkTest(int...counts) {
        this.counts = IntStream.of(counts);
    }

    public abstract String start(String line, String[] params, Map<String, Map<String, Object>>[] repositoryArray);
    public ReturnWorkTest paramsCheck(int size) {
        counts.anyMatch(v -> v == size);
        return this;
    }
}
