package bin.apply.mode;

import bin.exception.FileException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum FileMode {
    OTL(".otl"),
    OTLC(".otlc");

    private final String extension;

    public boolean check(File file) {
        return check(file.getPath());
    }

    public boolean check(String path) {
        return path
                .toLowerCase(Locale.ROOT)
                .endsWith(extension);
    }

    @NotNull
    public static FileMode checkFile(File file) {
        String filePath = file.getPath().toLowerCase(Locale.ROOT);
        for (FileMode fm : FileMode.values()) {
            if (filePath.endsWith(fm.extension)) return fm;
        }
        throw FileException.DO_NOT_SUPPORT.getThrow(filePath);
    }

    public static String extensionList() {
        return Stream.of(FileMode.values())
                .map(FileMode::getExtension)
                .collect(Collectors.joining(", "));
    }
}
