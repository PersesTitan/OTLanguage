import bin.apply.Setting;
import bin.apply.sys.make.StartLine;
import bin.exception.FileException;
import com.sun.management.OperatingSystemMXBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

import static bin.apply.Controller.br;
import static bin.apply.Controller.bw;
import static bin.apply.sys.item.SystemSetting.extensionCheck;
import static bin.token.Token.*;

public class Main extends Setting {
    private final String patternText = START + "[0-9]+ ";
    private final Pattern pattern = Pattern.compile(patternText);

    public static void main(String[] args) {
//        List<String[]> test = new ArrayList<>() {{
//            add(new String[]{"test/origin_test.otl"});
//        }};
//        test.forEach(v -> {
//            try {
//                new Main(v);
//            } catch (FileException e) {
//                FileException.printErrorMessage(e, Setting.path);
//            }
//        });

        args = new String[]{"hello.otl"};

        try {
            new Main(args);
        } catch (FileException e) {
            FileException.printErrorMessage(e, Setting.mainPath);
        } finally {
            try {
                br.close();
                bw.close();
            } catch (IOException ignored) {}
        }
    }

    private Main(String[] args) {
        if (args.length <= 0) throw FileException.noFindError();
        File file = new File(args[0]); //파일 생성
        Setting.mainPath = file.getAbsolutePath();
        Setting.path = file.getAbsoluteFile().getParent();
        if (!file.exists()) throw FileException.pathNoHaveError();
        else if (!file.isFile()) throw FileException.isNotFileError();
        else if (!file.canRead()) throw FileException.noReadError();
        else if (!extensionCheck(file.getName())) throw FileException.rightExtension();
        Setting.firstStart();

        String text;
        long count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(mainPath, StandardCharsets.UTF_8))) {
            while ((text = reader.readLine()) != null) Setting.total.append(++count).append(" ").append(text).append("\n");
            StartLine.startLine(Setting.total.toString(), mainPath, repository);
        } catch (IOException ignored) {}

        pause();
    }

    private void pause() {
        while (true) {}
    }
}
