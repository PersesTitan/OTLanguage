import bin.apply.Controller;
import bin.apply.Setting;
import bin.apply.sys.item.RunType;
import bin.apply.sys.make.Bracket;
import bin.apply.sys.make.StartLine;
import bin.exception.FileException;

import java.io.*;

import static bin.apply.Controller.*;
import static bin.apply.sys.item.Separator.*;
import static bin.apply.sys.item.SystemSetting.extension;
import static bin.apply.sys.item.SystemSetting.extensionCheck;
import static bin.token.LoopToken.LOOP_TOKEN;

public class Main extends Setting {
    public static void main(String[] args) {
        try {
            if (isWindow) startWindow(args);
            else new Main(args);
        } catch (FileException e) {
            new FileException().printErrorMessage(e, Setting.mainPath);
            if (isWindow) {
                try {System.in.read();}
                catch (IOException ignored) {}
            }
        } finally {try {br.close(); bw.close();} catch (IOException ignored) {}}
    }

    // OS : window
    private static void startWindow(String[] args) {
        new Main(args.length == 0
                ? new String[]{INSTALL_PATH}
                : new String[]{INSTALL_PATH, args[0]});
    }

    public Main(String[] args) {
        if (args.length <= 0) throw new FileException().noFindError();
        else if (args.length == 1) runType = RunType.Shell;     // 현재 파일 위치
        else if (args.length == 2) runType = RunType.Normal;    // 현재 파일 이름
        else throw new FileException().noValidValues();

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
        // 파일을 읽고 Setting.total 에 값을 넣는 작업
        Controller.readFile(mainPath, Setting.total);
        StartLine.startLine(Setting.total.toString(), mainPath, repository);
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
