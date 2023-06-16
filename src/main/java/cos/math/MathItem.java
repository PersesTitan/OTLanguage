package cos.math;

import bin.apply.item.Item;

import java.util.Random;

class MathItem extends Random implements Item {
    public static float roundF(float number) {
        return Math.round(number);
    }

    public static double roundD(double number) {
        return Math.round(number);
    }

    public static int absI(int v) {
        return Math.abs(v);
    }

    public static long absL(long v) {
        return Math.abs(v);
    }

    public static float absF(float v) {
        return Math.abs(v);
    }

    public static double absD(double v) {
        return Math.abs(v);
    }

    public int boundInt(int bound) {
        if (bound <= 0) throw MathException.RANDOM_BOUND_ERROR.getThrow(bound);
        return super.nextInt(bound);
    }

    public long boundLong(long bound) {
        if (bound <= 0) throw MathException.RANDOM_BOUND_ERROR.getThrow(bound);
        return super.nextLong(bound);
    }

    public float boundFloat(float bound) {
        if (bound <= 0) throw MathException.RANDOM_BOUND_ERROR.getThrow(bound);
        return super.nextFloat(bound);
    }

    public double boundDouble(double bound) {
        if (bound <= 0) throw MathException.RANDOM_BOUND_ERROR.getThrow(bound);
        return super.nextDouble(bound);
    }

    public int originInt(int a, int b) {
        return super.nextInt(a, b);
    }

    public long originLong(long a, long b) {
        return super.nextLong(a, b);
    }

    public float originFloat(float a, float b) {
        return super.nextFloat(a, b);
    }

    public double originDouble(double a, double b) {
        return super.nextDouble(a, b);
    }

    @Override
    public String toString() {
        return this.itemToString(MathToken.MATH);
    }
}
