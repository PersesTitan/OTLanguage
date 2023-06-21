package cos.math;

import bin.apply.Repository;
import work.ResetWork;

public class Reset implements ResetWork, Repository, MathToken {
    @Override
    public String version() {
        return "1.0.0";
    }

    @Override
    public void reset() {
        create(MATH, MathItem.class, v -> new MathItem());

        methodWorks.addStatic(MATH, SIN, d, Math::sin, d);
        methodWorks.addStatic(MATH, COS, d, Math::cos, d);
        methodWorks.addStatic(MATH, TAN, d, Math::tan, d);

        methodWorks.addStatic(MATH, CEIL, d, Math::ceil, d);
        methodWorks.addStatic(MATH, FLOOR, d, Math::floor, d);
        methodWorks.addStatic(MATH, ROUND_F, f, MathItem::roundF, f);
        methodWorks.addStatic(MATH, ROUND_D, d, MathItem::roundD, d);

        methodWorks.addStatic(MATH, POW, d, d, Math::pow, d);
        methodWorks.addStatic(MATH, SQRT, d, Math::sqrt, d);
        methodWorks.addStatic(MATH, EXP, d, Math::exp, d);
        methodWorks.addStatic(MATH, LOG, d, Math::log, d);

        methodWorks.addStatic(MATH, PI, () -> Math.PI, d);
        methodWorks.addStatic(MATH, E, () -> Math.E, d);

        methodWorks.addStatic(MATH, ABS_I, i, MathItem::absI, i);
        methodWorks.addStatic(MATH, ABS_L, l, MathItem::absL, l);
        methodWorks.addStatic(MATH, ABS_F, f, MathItem::absF, f);
        methodWorks.addStatic(MATH, ABS_D, d, MathItem::absD, d);

        methodWorks.add(MATH, RANDOM_B, MathItem::nextBoolean, b);
        methodWorks.<MathItem, Integer>add(MATH, RANDOM_I, MathItem::nextInt, i);
        methodWorks.<MathItem, Long>add(MATH, RANDOM_L, MathItem::nextLong, l);
        methodWorks.<MathItem, Float>add(MATH, RANDOM_F, MathItem::nextFloat, f);
        methodWorks.<MathItem, Double>add(MATH, RANDOM_D, MathItem::nextDouble, d);

        methodWorks.add(MATH, RANDOM_I, i, MathItem::boundInt, i);
        methodWorks.add(MATH, RANDOM_L, l, MathItem::boundLong, l);
        methodWorks.add(MATH, RANDOM_F, f, MathItem::boundFloat, f);
        methodWorks.add(MATH, RANDOM_D, d, MathItem::boundDouble, d);

        methodWorks.add(MATH, RANDOM_I, i, i, MathItem::originInt, i);
        methodWorks.add(MATH, RANDOM_L, l, l, MathItem::originLong, l);
        methodWorks.add(MATH, RANDOM_F, f, f, MathItem::originFloat, f);
        methodWorks.add(MATH, RANDOM_D, d, d, MathItem::originDouble, d);
    }
}
