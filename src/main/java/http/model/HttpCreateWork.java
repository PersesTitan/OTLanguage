package http.model;

public interface HttpCreateWork {
    boolean check(String line);
    void start(String line);
}
