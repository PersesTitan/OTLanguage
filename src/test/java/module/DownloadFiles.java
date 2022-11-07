package module;

import java.io.*;
import java.net.URL;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public class DownloadFiles {
    private static final String SEPARATOR_HOME = System.getProperty("user.home");
    private final String SEPARATOR_FILE = System.getProperty("file.separator");
    private final String INSTALL_PATH = SEPARATOR_HOME + SEPARATOR_FILE + ".otl";
    private final String MODULE_PATH = INSTALL_PATH + SEPARATOR_FILE + "module" + SEPARATOR_FILE;

    public static void main(String[] args) {
        new DownloadFiles("origin");
        new DownloadFiles("poison");
    }

    private DownloadFiles(String fileName) {
        boolean bool = true;
        String urlPath = "https://raw.githubusercontent.com/OTLanguage/module/main/" + fileName + "/system.otls";
        try (BufferedReader reader = readUrl(urlPath)) {
            for (String lines : reader.lines().toList()) {
                StringTokenizer tokenizer = new StringTokenizer(lines, "~");
                String line = null;
                while (tokenizer.hasMoreTokens()) line = tokenizer.nextToken();
                if (line == null) bool = false;
                else if (bool) {
                    String COMPULSION = "compulsion";   // 강제
                    String ALTERATION = "alteration";   // 변경
                    String OPERATE = "operate";         // 동작
                    if (line.startsWith(COMPULSION))
                        downloadModule(fileName, fileName + ".otlm", COMPULSION);
                    else if (line.startsWith(ALTERATION))
                        downloadModule(fileName, fileName + ".otlm", ALTERATION);
                    else if (line.startsWith(OPERATE))
                        downloadModule(fileName, fileName + ".otlm", OPERATE);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println(fileName + "은 존재하지 않는 모듈입니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // moduleName = poison
    // fileName = poison.otlm
    private void downloadModule(String moduleName, String fileName, String type) {
        // 모듈
        String urlPath = String.format("https://raw.githubusercontent.com/OTLanguage/module/main/%s/%s", moduleName, type + ".otlm");
        System.out.println(urlPath);
        try (BufferedReader reader = readUrl(urlPath)) {
            String createPath = MODULE_PATH + type + SEPARATOR_FILE + fileName;
            createModuleFile(createPath, reader.lines());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedReader readUrl(String urlPath) throws IOException {
        return new BufferedReader(new InputStreamReader(new URL(urlPath).openStream()));
    }

    private void createModuleFile(String fileName, Stream<String> stream) throws IOException {
        File file = new File(fileName);
        boolean bool1 = new File(fileName.substring(0, fileName.lastIndexOf("/"))).mkdirs();
        boolean bool2 = file.createNewFile();
        if (bool1 || bool2) System.out.println("파일을 생성하였습니다.");
        else System.out.println("파일을 업데이트하였습니다.");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            ListIterator<String> list = stream.toList().listIterator();
            while (list.hasNext()) {
                writer.write(list.next());
                if (list.hasNext()) writer.newLine();
            }
            writer.flush();
        }
    }
}
