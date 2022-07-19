package origin.thead.model;

public interface ThreadWork {
    boolean check(String line);
    void start(String line);
}
