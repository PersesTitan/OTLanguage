package bin.apply.repository.function;

@FunctionalInterface
public interface BiSupplier<R, A> {
    R get(A a);
}
