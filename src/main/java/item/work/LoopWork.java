package item.work;

public interface LoopWork {
    boolean check(String total);

    default int countBlank(String line) {
        int count = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) != ' ') break;
            count++;
        }
        return count;
    }
}
