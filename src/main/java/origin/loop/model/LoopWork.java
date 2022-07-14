package origin.loop.model;

public interface LoopWork {
    boolean check(String line);
    void start(String line);
    String getPattern();
}
