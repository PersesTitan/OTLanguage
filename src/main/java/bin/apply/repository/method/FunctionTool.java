package bin.apply.repository.method;

interface FunctionTool {
    @FunctionalInterface
    interface BiSupplier<R, A> {
        R get(A a);
    }

    @FunctionalInterface
    interface CiConsumer<A, B, C> {
        void accept(A a, B b, C c);
    }

    @FunctionalInterface
    interface CiFunction<A, B, C, R> {
        R apply(A a, B b, C c);
    }

    @FunctionalInterface
    interface NoConsumer {
        void accept();
    }
}
