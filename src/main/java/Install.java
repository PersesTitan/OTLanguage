import bin.apply.sys.make.StartLine;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static bin.apply.sys.item.Separator.*;
import static bin.token.Token.ACCESS;

public class Install {
    // system.otls
    public static void main(String[] args) {
        if (args.length != 0) new Install(args[0]);
        else System.out.printf("%s파일명을 입력해주세요.%s\n", "\033[0;31m", "\033[0m");
    }

    private static final String SEPARATOR_HOME = System.getProperty("user.home");
    private static final String SEPARATOR_FILE = System.getProperty("file.separator");
    private static final String INSTALL_PATH = SEPARATOR_HOME + SEPARATOR_FILE + ".otl";
    private static final String MODULE_PATH = INSTALL_PATH + SEPARATOR_FILE + "module" + SEPARATOR_FILE;

    String COMPULSION = "compulsion";   // 강제
    String ALTERATION = "alteration";   // 변경
    String OPERATE = "operate";         // 동작

    private final int MODULE = 0;
    private final int CLASS = 1;
    private final int FILE = 2;

    // fileName: poison
    private Install(String fileName) {
        String urlPath = "https://raw.githubusercontent.com/OTLanguage/module/main/" + fileName + "/system.otls";
        try (BufferedReader reader = readUrl(urlPath)) {
            System.out.printf("%s다운로드를 시작합니다...%s\n", "\033[0;32m", "\033[0m");
            List<String> list = new ArrayList<>();
            boolean bool = false;
            for (String line : reader.lines().map(String::strip).toList()) {
                if (line.endsWith(":")) bool = line.equals("system:");
                if (!bool) list.add(line);
            }
            list.removeIf(String::isBlank);

            download(fileName, list);
            System.out.printf("%s다운로드를 완료하였습니다.%s\n", "\033[0;32m", "\033[0m");
        } catch (IOException e) {
            System.out.printf("%s%s파일을 찾을 수 없습니다.%s\n", "\033[0;31m", fileName, "\033[0m");
        }
    }

    private final AtomicInteger count = new AtomicInteger(1);
    private void download(String fileName, List<String> list) throws IOException {
        int type = -1;
        StringBuilder builder = new StringBuilder();
        long size = list.stream()
                .filter(Predicate.not(v -> v.endsWith(":") || v.isBlank()))
                .count();

        for (String line : list) {
            if (line.isBlank()) continue;
            if (line.endsWith(":")) {
                if (type != -1 && !builder.isEmpty()) {
                    runDownload(type, builder.toString(), fileName, size);
                    builder.setLength(0);
                }
                switch (line) {
                    case "module:" -> type = MODULE;
                    case "class:" -> type = CLASS;
                    case "file:" -> type = FILE;
                }
            } else builder.append(line).append("\n");
        }

        if (type != -1) {
            runDownload(type, builder.toString(), fileName, size);
        }

        File file = new File(SYSTEM_PATH);
        file.getParentFile().mkdirs();
        file.createNewFile();
        // 파일 중복 제거
        try (BufferedWriter fw = new BufferedWriter(new FileWriter(file, false))) {
            for (String s : getKind()) {
                fw.write(s);
                fw.newLine();
            }
            fw.flush();
            System.out.printf("%ssystem.otls add... ok%s\n", "\033[0;32m", "\033[0m");
        }
    }

    private void runDownload(int type, String total, String fileName, long size) throws IOException {
        switch (type) {
            case MODULE ->
                total.lines().forEach(line -> {
                    print(size);
                    if (line.startsWith(COMPULSION)) {
                        System.out.printf("%s.%s", "\033[0;32m", "\033[0m");
                        String urlValue = getModule(fileName, COMPULSION + ".otlm");
                        String localPath = getDownloadPath(fileName, COMPULSION);
                        install(urlValue, localPath);
                        System.out.printf("%s.%s", "\033[0;32m", "\033[0m");
                        System.out.printf("%s add module %s", "\033[0;32m", "\033[0m");
                    } else if (line.startsWith(ALTERATION)) {
                        System.out.printf("%s.%s", "\033[0;32m", "\033[0m");
                        String urlValue = getModule(fileName, ALTERATION + ".otlm");
                        String localPath = getDownloadPath(fileName, ALTERATION);
                        install(urlValue, localPath);
                        System.out.printf("%s.%s", "\033[0;32m", "\033[0m");
                        System.out.printf("%s add module %s", "\033[0;32m", "\033[0m");
                    } else if (line.startsWith(OPERATE)) {
                        System.out.printf("%s.%s", "\033[0;32m", "\033[0m");
                        String urlValue = getModule(fileName, OPERATE + ".otlm");
                        String localPath = getDownloadPath(fileName, OPERATE);
                        install(urlValue, localPath);
                        System.out.printf("%s.%s", "\033[0;32m", "\033[0m");
                        System.out.printf("%s add module %s", "\033[0;32m", "\033[0m");
                    }
                    System.out.println(line);
                });

            case CLASS -> total.lines().forEach(line -> {
                print(size);
                System.out.printf("%s.%s", "\033[0;32m", "\033[0m");
                String urlValue = getKlass(fileName, line + ".class");
                String localPath = getDownloadPath(line);
                install(urlValue, localPath);
                System.out.printf("%s.%s", "\033[0;32m", "\033[0m");
                System.out.printf("%s add %s", "\033[0;32m", "\033[0m");
                System.out.println(line);
            });

            case FILE -> total.lines().forEach(line -> {
                print(size);
                System.out.printf("%s.%s", "\033[0;32m", "\033[0m");
                String urlValue = getFiles(fileName, line);
                String localPath = getFilesPath(fileName, line.replace(ACCESS, SEPARATOR_FILE));
                install(urlValue, localPath);
                System.out.printf("%s.%s", "\033[0;32m", "\033[0m");
                System.out.println(line);
            });
        }
    }

    private void print(long size) {
        System.out.printf("%s(%d/%d)install%s", "\033[0;32m", size, count.getAndIncrement(), "\033[0m");
        System.out.printf("%s.%s", "\033[0;32m", "\033[0m");
    }

    /*
     * 파일은 다운로드 하는 로직
     */
    private BufferedReader readUrl(String urlPath) throws IOException {
        return new BufferedReader(new InputStreamReader(new URL(urlPath).openStream()));
    }

    // 설치하는 로직
    private void install(String urlValue, String localPath) {
        System.out.printf("%s.%s", "\033[0;32m", "\033[0m");
        new File(localPath.substring(0, localPath.lastIndexOf(SEPARATOR_FILE))).mkdirs();
        System.out.printf("%s.%s", "\033[0;32m", "\033[0m");
        try (ReadableByteChannel rbc = Channels.newChannel(new URL(urlValue).openStream());
             FileOutputStream fo = new FileOutputStream(localPath)) {
            System.out.printf("%s.%s", "\033[0;32m", "\033[0m");
            fo.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            System.out.printf("%s.%s", "\033[0;32m", "\033[0m");
        } catch (IOException ignored) {
        }
    }

    // moduleName: origin
    // fileName: cos~work~compulsion.class
    private String getKlass(String moduleName, String fileName) {
        fileName = fileName.substring(fileName.lastIndexOf("~") + 1);
        // fileName = cos~poison~method
        return String.format("https://raw.githubusercontent.com/OTLanguage/module/main/%s/%s", moduleName, fileName);
    }

    // moduleName : music
    // fileName: A.wav
    private String getFiles(String name, String fileName) {
        fileName = fileName.replace(ACCESS, "/");
        return String.format("https://raw.githubusercontent.com/OTLanguage/module/main/%s/%s", name, fileName);
    }

    private String getFilesPath(String name, String file) {
        return getPath(MODULE_PATH, name, file);
    }

    // fileName: cos~work~compulsion
    private String getDownloadPath(String type) {
        // HOME/User/.otl / analyzer / cos/work/compulsion
        String path = INSTALL_PATH + SEPARATOR_FILE + "analyzer" + SEPARATOR_FILE + type.replace("~", SEPARATOR_FILE);
        return path + ".class";
    }

    // moduleName: origin
    // fileName: compulsion.otlm
    private String getModule(String moduleName, String fileName) {
        return String.format("https://raw.githubusercontent.com/OTLanguage/module/main/%s/%s", moduleName, fileName);
    }

    // moduleName: origin
    // fileName: compulsion
    private String getDownloadPath(String moduleName, String type) {
        // User/Test/.otl/module/ compulsion / origin.otlm
        return MODULE_PATH + type + SEPARATOR_FILE + moduleName + ".otlm";
    }

    private List<String> getKind() {
        Set<String> set = new HashSet<>();
        for (File files : Objects.requireNonNull(new File(COMPULSION_PATH).listFiles())) {
            set.add(files.getName());
        }
        for (File files : Objects.requireNonNull(new File(ALTERATION_PATH).listFiles())) {
            set.add(files.getName());
        }
        for (File files : Objects.requireNonNull(new File(OPERATE_PATH).listFiles())) {
            set.add(files.getName());
        }
        set.removeIf(Predicate.not(v -> v.endsWith(MODULE_EXTENSION)));

        List<String> list = new ArrayList<>();
        set.stream()
                .map(v -> v.substring(0, v.lastIndexOf('.')))
                .forEach(fileName -> {
                    String urlPath = "https://raw.githubusercontent.com/OTLanguage/module/main/" + fileName + "/system.otls";
                    try (BufferedReader reader = readUrl(urlPath)) {
                        boolean bool = false;
                        for (String line : reader.lines().map(String::strip).toList()) {
                            if (line.endsWith(":")) bool = line.equals("system:");
                            else if (bool) list.add(line);
                        }
                    } catch (IOException e) {
                        System.out.printf("%s%s파일을 찾을 수 없습니다.%s\n", "\033[0;31m", fileName, "\033[0m");
                    }
                });
        list.removeIf(String::isBlank);
        return list;
    }
}
