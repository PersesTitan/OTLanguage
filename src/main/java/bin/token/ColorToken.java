package bin.token;

public interface ColorToken {
    // Reset
    String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    String BLACK = "\033[0;30m";   // BLACK
    String RED = "\033[0;31m";     // RED
    String GREEN = "\033[0;32m";   // GREEN
    String YELLOW = "\033[0;33m";  // YELLOW
    String BLUE = "\033[0;34m";    // BLUE
    String PURPLE = "\033[0;35m";  // PURPLE
    String CYAN = "\033[0;36m";    // CYAN
    String WHITE = "\033[0;37m";   // WHITE

    String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
}
