import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

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

    // fileName: poison
    private Install(String fileName) {
        boolean bool = true;
        String urlPath = "https://raw.githubusercontent.com/OTLanguage/module/main/" + fileName + "/system.otls";
        try (BufferedReader reader = readUrl(urlPath)) {
            System.out.printf("%s다운로드를 시작합니다...%s\n", "\033[0;32m", "\033[0m");
            for (String line : reader.lines().toList()) {
                if (bool) {
                    if (line.startsWith(COMPULSION)) {
                        String urlValue = getModule(fileName, COMPULSION + ".otlm");
                        String localPath = getDownloadPath(fileName, COMPULSION);
                        install(urlValue, localPath);
                    } else if (line.startsWith(ALTERATION)) {
                        String urlValue = getModule(fileName, ALTERATION + ".otlm");
                        String localPath = getDownloadPath(fileName, ALTERATION);
                        install(urlValue, localPath);
                    } else if (line.startsWith(OPERATE)) {
                        String urlValue = getModule(fileName, OPERATE + ".otlm");
                        String localPath = getDownloadPath(fileName, OPERATE);
                        install(urlValue, localPath);
                    } else bool = false;
                } else {
                    String urlValue = getKlass(fileName, line + ".class");
                    String localPath = getDownloadPath(line);
                    install(urlValue, localPath);
                }
                if (!line.isBlank()) System.out.println(line);
            }
            System.out.printf("%s다운로드를 완료하였습니다.%s\n", "\033[0;32m", "\033[0m");
        } catch (IOException e) {
            System.out.printf("%s%s파일을 찾을 수 없습니다.%s\n", "\033[0;31m", fileName, "\033[0m");
        }
    }

    private BufferedReader readUrl(String urlPath) throws IOException {
        return new BufferedReader(new InputStreamReader(new URL(urlPath).openStream()));
    }

    // 설치하는 로직
    private void install(String urlValue, String localPath) {
        new File(localPath.substring(0, localPath.lastIndexOf(SEPARATOR_FILE))).mkdirs();
        try (ReadableByteChannel rbc = Channels.newChannel(new URL(urlValue).openStream());
             FileOutputStream fo = new FileOutputStream(localPath)) {
            fo.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // moduleName: origin
    // fileName: cos~work~compulsion.class
    private String getKlass(String moduleName, String fileName) {
        fileName = fileName.substring(fileName.lastIndexOf("~") + 1);
        // fileName = cos~poison~method
        return String.format("https://raw.githubusercontent.com/OTLanguage/module/main/%s/%s", moduleName, fileName);
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
}