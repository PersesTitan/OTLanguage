import bin.apply.Setting;
import bin.apply.sys.item.RunType;
import bin.apply.sys.make.StartLine;
import bin.exception.FileException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static bin.apply.Controller.*;
import static bin.apply.sys.item.Separator.SEPARATOR_HOME;
import static bin.apply.sys.item.Separator.SEPARATOR_LINE;
import static bin.apply.sys.item.SystemSetting.extensionCheck;
import static bin.token.LoopToken.LOOP_TOKEN;

public class Main extends Setting {

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

        args = new String[]{SEPARATOR_HOME, "hello.otl"};
//        args = new String[]{SEPARATOR_HOME};

        try {
            if (System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("win"))
                new Main(args.length == 0
                    ? new String[]{SEPARATOR_HOME}
                    : new String[]{SEPARATOR_HOME, args[0]});
            else new Main(args);
        } catch (FileException e) {
            FileException.printErrorMessage(e, Setting.mainPath);
        } finally {try {br.close(); bw.close();} catch (IOException ignored) {}}
    }

    private Main(String[] args) {
        if (args.length <= 0) throw FileException.noFindError();
        else if (args.length == 1) runType = RunType.Shell;     // 현재 파일 위치
        else if (args.length == 2) runType = RunType.Normal;    // 현재 파일 이름

        Setting.path = args[0];
        if (runType.equals(RunType.Normal)) normal(args);
        else if (runType.equals(RunType.Shell)) {
            try {shell();} catch (NullPointerException ignored) {}
        }
    }

    private void normal(String[] args) {
        File file = new File(args[1]); //파일 생성
        Setting.mainPath = file.getAbsolutePath();
        Setting.path = file.getAbsoluteFile().getParent();
        if (!file.exists()) throw FileException.pathNoHaveError();
        else if (!file.isFile()) throw FileException.isNotFileError();
        else if (!file.canRead()) throw FileException.noReadError();
        else if (!extensionCheck(file.getName())) throw FileException.rightExtension();
        Setting.firstStart();

        try (FileReader fileReader = new FileReader(mainPath, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(fileReader)) {
            for (int i = 1;;i++) {
                String line = reader.readLine();
                if (line == null) break;
                Setting.total.append(i).append(" ").append(line.stripIndent()).append(SEPARATOR_LINE);
            }
            StartLine.startLine(Setting.total.toString(), mainPath, repository);
        } catch (IOException ignored) {}
        pause();
    }

    private void shell() {
        String fileName = "temporary";
        StringBuilder total = new StringBuilder();
        Setting.firstStart();
        while (true) {
            System.out.print(">>> ");

            String line = scanner().strip();
            if (line.equals("1 끝")) break;
            else if (line.endsWith("{")) {
                boolean check = false;
                int count = 0;
                int bracketCount = 1;
                total.setLength(0);
                total.append(++count).append(" ").append(line).append("\n");
                while (true) {
                    System.out.print("--- ");
                    line = scanner().strip();
                    total.append(++count).append(" ").append(line).append("\n");
                    if (line.endsWith("{")) bracketCount++;
                    else if (line.startsWith("}")) bracketCount--;

                    if (bracketCount < 0) break;
                    else if (bracketCount == 0) {
                        if (check) break;
                        else {
                            System.out.print("--- ");
                            line = scanner().strip();
                            check = line.equals("");
                        }
                    }
                }
                LOOP_TOKEN.put(fileName, total.toString());
                StartLine.startLine(total.toString(), fileName, repository);
            } else StartLine.startLine("1 " + line, fileName, repository);
        }
    }

    private void pause() {
        for (;;) {}
    }
}
