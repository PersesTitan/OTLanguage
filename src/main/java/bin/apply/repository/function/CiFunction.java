package bin.apply.repository.function;

@FunctionalInterface
public interface CiFunction<A, B, C, R> {
    R apply(A a, B b, C c);
}
