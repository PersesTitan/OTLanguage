package bin.orign.math.random;

import work.v3.ReturnWorkV3;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import static bin.check.VariableCheck.isFloat;

public class RandomFloat extends ReturnWorkV3 {
    public RandomFloat(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        Random random = new Random();
        if (params.length == 1) {
            if (params[0].isBlank()) return Float.toString(random.nextFloat());
            else {
                if (!isFloat(params[0])) return null;
                float num = Float.parseFloat(params[0]);
                return Float.toString(random.nextFloat(num));
            }
        } else {
            if (!isFloat(params[0])) return null;
            else if (!isFloat(params[1])) return null;
            float num1 = Float.parseFloat(params[0]);
            float num2 = Float.parseFloat(params[1]);
            return Float.toString(random.nextFloat(num1, num2));
        }
    }
}
