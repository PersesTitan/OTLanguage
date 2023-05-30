package bin.apply.repository.function;

@FunctionalInterface
public interface CiConsumer<A, B, C> {
    void accept(A a, B b, C c);
}
