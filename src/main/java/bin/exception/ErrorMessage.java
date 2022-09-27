package bin.exception;

public interface ErrorMessage {
    static void printErrorMessage(RuntimeException e, String subMessage) {
        String message = "OTLanguage." + e.getClass().getSimpleName() + ": " +
                e.getMessage() +
                (subMessage.isBlank() ? "" : subMessage.replaceAll("(^|\\n)","\n\totl "));
        System.err.println(message);
    }

    static void printErrorMessage(RuntimeException e, String subMessage, String path) {
        String message = "OTLanguage." + e.getClass().getSimpleName() + ": " +
                e.getMessage() +
                "\n\totl Path(" + path + ")" +
                (subMessage.isBlank() ? "" : subMessage.replaceAll("(^|\\n)","\n\totl "));
        System.err.println(message);
    }

    static void printErrorMessage(RuntimeException e, String subMessage, String path, String line, long position) {
        String message = "OTLanguage." + e.getClass().getSimpleName() + ": " +
                e.getMessage() +
                "\n\totl file location where it occurred(" + path + ":" + position + ")" +
                "\n\totl line that occurred(" + line + ")" +
                (subMessage.isBlank() ? "" : subMessage.replaceAll("(^|\\n)","\n\totl "));
        System.err.println(message);
    }
}
