package bin.exception;

import bin.apply.Setting;
import bin.apply.sys.item.Color;
import bin.apply.sys.item.RunType;

import static bin.apply.Setting.runType;

public interface ErrorMessage {
    static void printErrorMessage(RuntimeException e, String subMessage) {
        String message = "OTLanguage." + e.getClass().getSimpleName() + ": " +
                e.getMessage() +
                (subMessage.isBlank() ? "" : subMessage.replaceAll("(^|\\n)","\n\totl "));
        if (runType.equals(RunType.Shell)) Setting.errorMessage(message);
        else System.err.println(message);
    }

    static void printErrorMessage(RuntimeException e, String subMessage, String path) {
        String message = "OTLanguage." + e.getClass().getSimpleName() + ": " +
                e.getMessage() +
                "\n\totl Path(" + path + ")" +
                (subMessage.isBlank() ? "" : subMessage.replaceAll("(^|\\n)","\n\totl "));
        if (runType.equals(RunType.Shell)) Setting.errorMessage(message);
        else System.err.println(message);
    }

    static void printErrorMessage(RuntimeException e, String subMessage, String path, String line, long position) {
        String message = "OTLanguage." + e.getClass().getSimpleName() + ": " +
                e.getMessage() +
                (path == null ? "" : "\n\totl file location where it occurred(" + path + ":" + position + ")") +
                "\n\totl line that occurred(" + line + ")" +
                (subMessage.isBlank() ? "" : subMessage.replaceAll("(^|\\n)","\n\totl "));
        if (runType.equals(RunType.Shell)) Setting.errorMessage(message);
        else System.err.println(message);
    }
}
