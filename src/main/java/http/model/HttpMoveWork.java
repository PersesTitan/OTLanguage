package http.model;

public interface HttpMoveWork {
    boolean check(String line);
    void move(String line);
}
