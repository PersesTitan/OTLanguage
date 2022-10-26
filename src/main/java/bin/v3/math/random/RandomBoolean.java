package bin.v3.math.random;

import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class RandomBoolean extends ReturnWorkV3 {
    public RandomBoolean(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        return new Random().nextBoolean() ? "ㅇㅇ" : "ㄴㄴ";
    }
}
