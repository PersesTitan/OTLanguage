package bin.v3.math.random;

import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import static bin.check.VariableCheck.isDouble;

public class RandomDouble extends ReturnWorkV3 {
    public RandomDouble(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        Random random = new Random();
        if (params.length == 1) {
            if (params[0].isBlank()) return Double.toString(random.nextDouble());
            else {
                if (!isDouble(params[0])) return null;
                double num = Double.parseDouble(params[0]);
                return Double.toString(random.nextDouble(num));
            }
        } else {
            if (!isDouble(params[0])) return null;
            else if (!isDouble(params[1])) return null;
            double num1 = Double.parseDouble(params[0]);
            double num2 = Double.parseDouble(params[1]);
            return Double.toString(random.nextDouble(num1, num2));
        }
    }
}
