package bin.apply.sys.item;

import java.util.Arrays;
import java.util.Locale;

public interface SystemSetting {
    String[] extension = {".otl"};

    static boolean extensionCheck(String fileName) {
        fileName = fileName.toLowerCase(Locale.ROOT);
        return Arrays.stream(extension)
                .anyMatch(fileName::endsWith);
    }

    static String getExtension() {
        return String.join(", ", extension);
    }
}
