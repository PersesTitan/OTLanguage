import bin.apply.Setting;
import bin.apply.sys.item.RunType;
import bin.apply.sys.make.Bracket;
import bin.apply.sys.make.StartLine;
import bin.exception.FileException;
import bin.exception.VariableException;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static bin.apply.Controller.*;
import static bin.apply.sys.item.Separator.*;
import static bin.apply.sys.item.SystemSetting.extension;
import static bin.apply.sys.item.SystemSetting.extensionCheck;
import static bin.token.LoopToken.LOOP_TOKEN;

public class Main extends Setting {
    public static void main(String[] args) {
        try {
            // window
            if (System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("win")) {
                new Main(args.length == 0
                    ? new String[]{SEPARATOR_HOME}
                    : new String[]{SEPARATOR_HOME, args[0]});
                System.in.read();
            } else new Main(args);
        } catch (FileException e) {
            new FileException().printErrorMessage(e, Setting.mainPath);
        } catch (IOException ignored) {
        } finally {try {br.close(); bw.close();} catch (IOException ignored) {}}
    }

    public Main(String[] args) {
        if (args.length <= 0) throw new FileException().noFindError();
        else if (args.length == 1) runType = RunType.Shell;     // 현재 파일 위치
        else if (args.length == 2) runType = RunType.Normal;    // 현재 파일 이름
        else throw new FileException().noValidValues();

        Setting.path = args[0];
        if (runType.equals(RunType.Normal)) normal(args);
        else {
            try {
                // 임시 파일 생성
                File file = File.createTempFile("otl_", extension[0]);
                // 프로그램이 종료되면 임시파일 삭제
                file.deleteOnExit();
                Setting.mainPath = file.getAbsolutePath();
                Setting.path = file.getAbsoluteFile().getParent();
                shell(file);
            } catch (NullPointerException | IOException e) {if (StartLine.developmentMode) e.printStackTrace();}
        }
    }

    private void normal(String[] args) {
        File file = new File(args[1]); //파일 생성
        Setting.mainPath = file.getAbsolutePath();
        Setting.path = file.getAbsoluteFile().getParent();
        if (!file.exists()) throw new FileException().pathNoHaveError();
        else if (!file.isFile()) throw new FileException().isNotFileError();
        else if (!file.canRead()) throw new FileException().noReadError();
        else if (!extensionCheck(file.getName())) throw new FileException().rightExtension();

        try (FileReader fileReader = new FileReader(mainPath, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(fileReader)) {
            for (int i = 1;;i++) {
                String line = reader.readLine();
                if (line == null) break;
                Setting.total.append(i).append(" ").append(line.stripLeading()).append(SEPARATOR_LINE);
            }
            StartLine.startLine(Setting.total.toString(), mainPath, repository);
        } catch (IOException e) {if (StartLine.developmentMode) e.printStackTrace();}
    }

    private void shell(File file) {
        Bracket.getInstance().bracket("", file);
        String fileName = file.getName().substring(0, file.getName().indexOf('.'));

        StringBuilder total = new StringBuilder();
        while (true) {
            System.out.print(">>> ");
            String line = scanner().strip();

            if (line.equals("끝")) break;
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
                            check = scanner().strip().equals("");
                        }
                    }
                }
                LOOP_TOKEN.put(fileName, total.toString());
                StartLine.startLine(total.toString(), fileName, repository);
            } else Setting.start(line, line, repository);
        }
    }
}
