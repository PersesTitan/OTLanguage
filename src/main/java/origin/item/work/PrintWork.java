package origin.item.work;

public interface PrintWork {
    void start(String line);
    boolean check(String line) throws Exception;
}