package http.model;

public interface HttpWork {
    boolean check(String line);
    void start(String line);
}
