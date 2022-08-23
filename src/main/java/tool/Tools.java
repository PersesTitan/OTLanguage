package tool;

public interface Tools {
    // (...) => ...
    static String deleteBothEnds(String line) {
        return line.substring(1, line.length()-1);
    }

    static String[] splitArray(String line) {
        return line.strip().split("\\s*,\\s*");
    }
}
