package bin.v3.math.random;

import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import static bin.check.VariableCheck.isLong;

public class RandomLong extends ReturnWorkV3 {
    public RandomLong(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        Random random = new Random();
        if (params.length == 1) {
            if (params[0].isBlank()) return Long.toString(random.nextLong());
            else {
                if (!isLong(params[0])) return null;
                long num = Long.parseLong(params[0]);
                return Long.toString(random.nextLong(num));
            }
        } else {
            if (!isLong(params[0])) return null;
            else if (!isLong(params[1])) return null;
            long num1 = Long.parseLong(params[0]);
            long num2 = Long.parseLong(params[1]);
            return Long.toString(random.nextLong(num1, num2));
        }
    }
}
