package module.klass;

import java.lang.constant.ConstantDesc;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

public class KlassList {
    private final static Class<Integer> I = java.lang.Integer.class;
    private final static Class<Float> F = java.lang.Float.class;
    private final static Class<Long> L = java.lang.Long.class;
    private final static Class<Double> D = java.lang.Double.class;

    public static void main(String[] args) {
        ConstantDesc a = 1;
        ConstantDesc b = 2;
        ConstantDesc c = "aa";
        System.out.println(a);
        System.out.println(10/3);
        System.out.println(cast(Integer.class, 15) + cast(Float.class, 52f));
    }

    public static <T> T cast(Class<T> tClass, Object o) {
        return tClass.cast(o);
    }
}
