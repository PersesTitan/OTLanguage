import item.ActivityItem;
import item.Setting;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class Main extends Setting implements ActivityItem {

    public static void main(String[] args) throws Exception {
        args = new String[1]; args[0] = "./hello.otl";
        new Main(args);
    }

    private Main(String[] args) throws Exception {
        String path = args.length <= 0 ? showGUI() : args[0];
        if (!new File(path).canRead()) throw new Exception("파일을 읽을 수 없습니다.");
        if (!path.toLowerCase(Locale.ROOT).endsWith(".otl")) throw new Exception("확장자를 확인해주세요.");
        int count = 0;
        varClear();
        String text;
        System.out.println("================출력================");
        try (BufferedReader reader = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8))) {
            while ((text = reader.readLine()) != null) {
                idLine.put(count, text);
                total.append(text).append("\n");
                count++;
            }

            //괄호 -> 고유 아이디로 전환
            //괄호 계산
            String total = Setting.total.toString();
            total = bracket.bracket(total);
            for (String line : total.split("\\n")) start(line);
        }
        pause();
    }

    private String showGUI() throws Exception {
        final JFrame frame = new JFrame();
        final String[] extensions = {"otl"};
        final JFileChooser chooser = new JFileChooser();
        final FileNameExtensionFilter filter = new FileNameExtensionFilter(".otl", extensions);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chooser.setFileFilter(filter);
        int open = chooser.showOpenDialog(frame.getParent());
        if (open == JFileChooser.OPEN_DIALOG)
            return chooser.getSelectedFile().getPath();
        else throw new Exception("파일이 존재하지 않습니다.");
    }

    private void pause() {
        try {
            System.out.println("\n==================================");
            System.out.println("종료 : Enter");
            System.in.read();
        } catch (Exception ignored) {}
    }
}
