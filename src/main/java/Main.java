import bin.apply.Repository;
import bin.apply.Setting;
import bin.apply.sys.item.HpMap;
import bin.apply.sys.make.StartLine;
import bin.exception.FileException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Repeatable;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import static bin.apply.Controller.*;
import static bin.apply.sys.item.SystemSetting.extensionCheck;
import static bin.token.Token.*;
import static bin.token.VariableToken.TOTAL_LIST;

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
        } finally {try {br.close(); bw.close();} catch (IOException ignored) {}}
    }

    private Main(String[] args) {
        shell();
        System.exit(0);

        if (args.length <= 0) throw FileException.noFindError();
        File file = new File(args[0]); //파일 생성
        Setting.mainPath = file.getAbsolutePath();
        Setting.path = file.getAbsoluteFile().getParent();
        if (!file.exists()) throw FileException.pathNoHaveError();
        else if (!file.isFile()) throw FileException.isNotFileError();
        else if (!file.canRead()) throw FileException.noReadError();
        else if (!extensionCheck(file.getName())) throw FileException.rightExtension();
        Setting.firstStart();
        TOTAL_LIST.forEach(v -> COPY_REPOSITORY.put(v, new HpMap<>()));
//        repository.putAll((Map<String, Map<String, Object>>) COPY_REPOSITORY.clone());
//        new ReadOTLM().readSetting("system.otls");

        String text;
        long count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(mainPath, StandardCharsets.UTF_8))) {
            while ((text = reader.readLine()) != null) Setting.total.append(++count).append(" ").append(text).append("\n");
            StartLine.startLine(Setting.total.toString(), mainPath, repository);
        } catch (IOException ignored) {}

        pause();
    }

    private void shell() {
        Setting.firstStart();
        while (true) {
            System.out.print(">>> ");
            String line = scanner();
            if (line.equals("끝")) break;
            Setting.start(line, line, repository);
        }
    }

    private void pause() {
        for (;;) {}
    }
}
