package bin.calculator.number;

import static bin.check.VariableCheck.isLong;

public interface MinusCalculator {
    static long minus(long num1, long num2) {
        return num1 - num2;
    }

    static double minus(double num1, double num2) {
        return num1 - num2;
    }

    static double minus(double num1, long num2) {
        return num1 - num2;
    }

    static double minus(long num1, double num2) {
        return num1 - num2;
    }

    static String minus(String num1, String num2) {
        boolean numBool1 = isLong(num1);
        boolean numBool2 = isLong(num2);
        if (numBool1 && numBool2) {
            long n1 = Long.parseLong(num1);
            long n2 = Long.parseLong(num2);
            return Long.toString(minus(n1, n2));
        } else if (numBool1) {
            long n1 = Long.parseLong(num1);
            double n2 = Double.parseDouble(num2);
            return Double.toString(minus(n1, n2));
        } else if (numBool2) {
            double n1 = Double.parseDouble(num1);
            long n2 = Long.parseLong(num2);
            return Double.toString(minus(n1, n2));
        } else {
            double n1 = Double.parseDouble(num1);
            double n2 = Double.parseDouble(num2);
            return Double.toString(minus(n1, n2));
        }
    }
}
