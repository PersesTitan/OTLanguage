package bin.apply;

@FunctionalInterface
public interface OSConsumer {
    void accept(Object...values);
}
