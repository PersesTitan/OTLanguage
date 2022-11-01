package bin.orign.math.random;

import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import static bin.check.VariableCheck.isInteger;

public class RandomInteger extends ReturnWorkV3 {
    public RandomInteger(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        Random random = new Random();
        if (params.length == 1) {
            if (params[0].isBlank()) return Integer.toString(random.nextInt());
            else {
                if (!isInteger(params[0])) return null;
                int num = Integer.parseInt(params[0]);
                return Integer.toString(random.nextInt(num));
            }
        } else {
            if (!isInteger(params[0])) return null;
            else if (!isInteger(params[1])) return null;
            int num1 = Integer.parseInt(params[0]);
            int num2 = Integer.parseInt(params[1]);
            return Integer.toString(random.nextInt(num1, num2));
        }
    }
}
