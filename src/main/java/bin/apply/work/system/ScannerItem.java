package bin.apply.work.system;

import bin.exception.VariableException;
import bin.token.Token;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ScannerItem {
    private static Scanner scanner;
    private static Scanner getScanner() {
        if (scanner == null) {
            synchronized (ScannerItem.class) {
                scanner = new Scanner(System.in);
            }
        }
        return scanner;
    }

    public static int nextInt() {
        try {
            return getScanner().nextInt();
        } catch (InputMismatchException e) {
            throw VariableException.INPUT_VALUE_ERROR.getThrow(null);
        }
    }

    public static long nextLong() {
        try {
            return getScanner().nextLong();
        } catch (InputMismatchException e) {
            throw VariableException.INPUT_VALUE_ERROR.getThrow(null);
        }
    }

    public static float nextFloat() {
        try {
            return getScanner().nextFloat();
        } catch (InputMismatchException e) {
            throw VariableException.INPUT_VALUE_ERROR.getThrow(null);
        }
    }

    public static double nextDouble() {
        try {
            return getScanner().nextDouble();
        } catch (InputMismatchException e) {
            throw VariableException.INPUT_VALUE_ERROR.getThrow(null);
        }
    }

    public static boolean nextBool() {
        String value = getScanner().next();
        if (value.equals(Token.TRUE) || value.equals(Token.FALSE)) return value.equals(Token.TRUE);
        else throw VariableException.INPUT_VALUE_ERROR.getThrow(null);
    }

    public static char nextChar() {
        String value = getScanner().next();
        if (value.length() == 1) return value.charAt(0);
        else throw VariableException.INPUT_VALUE_ERROR.getThrow(null);
    }

    public static String nextLine() {
        return getScanner().nextLine();
    }
}
