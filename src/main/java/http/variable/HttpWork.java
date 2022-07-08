package http.variable;

public interface HttpWork {
    boolean check(String line);
    void start(String line) throws Exception;
}
