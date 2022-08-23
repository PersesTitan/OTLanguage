import event.Setting;
import http.server.Server;
import origin.exception.FileFailException;
import origin.exception.FileFailMessage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class Main extends Setting {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (Server.httpServerManager != null) Server.httpServerManager.stop();
        }));

        args = new String[1]; args[0] = "hello.otl";
        new Main(args);
    }

    private Main(String[] args) {

        String path = args.length <= 0 ? showGUI() : args[0];
        File file = new File(path); //파일 생성
        Setting.path = file.getAbsolutePath();
        if (!file.canRead()) throw new FileFailException(FileFailMessage.doNotReadFile);
        if (!path.toLowerCase(Locale.ROOT).endsWith(".otl")) throw new FileFailException(FileFailMessage.notMatchExtension);
        firstStart();

        String text;
        try (BufferedReader reader = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8))) {
            while ((text = reader.readLine()) != null) Setting.total.append(text).append("\n");
            //괄호 -> 고유 아이디로 전환 //괄호 계산
            String total = bracket.bracket(Setting.total.toString());
            for (String line : total.split("\\n")) {
                start(line); // 실행 메소드
            }
        } catch (IOException ignored) {}

        pause();
    }

    private String showGUI() {
        final JFrame frame = new JFrame();
        final String[] extensions = {"otl"};
        final JFileChooser chooser = new JFileChooser();
        final FileNameExtensionFilter filter = new FileNameExtensionFilter(".otl", extensions);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chooser.setFileFilter(filter);
        int open = chooser.showOpenDialog(frame.getParent());
        if (open == JFileChooser.OPEN_DIALOG)
            return chooser.getSelectedFile().getPath();
        else throw new FileFailException(FileFailMessage.doNotFindFile);
    }

    private void pause() {
        while (true) {}
    }
}
