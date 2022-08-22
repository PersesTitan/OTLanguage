package system.work;

public interface ThreadWork {
    boolean check(String line);
    void start(String line);
}
