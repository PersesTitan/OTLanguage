package bin.apply.repository.function;

@FunctionalInterface
public interface OSConsumer {
    void accept(Object...values);
}
