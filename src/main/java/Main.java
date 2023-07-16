import bin.apply.item.CodeItem;
import bin.apply.item.ShellCodeItem;
import bin.apply.line.LineTool;
import bin.apply.line.parser.LineParser;
import bin.apply.line.parser.ShellLineParser;
import bin.apply.mode.DebugMode;
import bin.apply.mode.FileMode;
import bin.apply.mode.RunMode;
import bin.token.work.SystemToken;
import bin.apply.work.system.Import;
import bin.exception.Error;
import bin.exception.FileException;
import bin.exception.SystemException;
import bin.token.Token;
import bin.token.check.CheckToken;

import java.io.*;
import java.util.*;

public class Main {
    /**
     * file information
     * <br> - version
     * <br> - path
     * @see bin.apply.item.CodeItem
     */

    public static void main(String[] args) {

        DebugMode.DEVELOPMENT.set();

        try {
            // version mode
            if (args[0].equals("-v")) System.out.print(CodeItem.VERSION);
            // compile mode
            else if (args[0].equals("-c")) {
                if (args.length == 2) {
                    File file = new File(args[1]);
                    System.out.print(switch (FileMode.checkFile(file)) {
                        case OTLC -> readCompile(file).getShell();
                        case OTL -> Import.readImport(file);
                    });
                } else throw SystemException.SYSTEM_ERROR.getThrow(null);
            } else {
                // SET RUN PATH
                CodeItem.RUN_PATH = args[0];
                switch (args.length) {
                    case 1 -> { RunMode.SHELL.set(); new Main(); }
                    case 2 -> new Main(new File(args[1]));
                    case 3 -> new Main(new File(args[1]), args[2]);
                    default -> {
                        String message = Arrays.toString(Arrays.copyOfRange(args, 1, args.length));
                        throw SystemException.VALID_VALUES_ERROR.getThrow(message);
                    }
                }
            }
        } catch (Error error) {
            if (DebugMode.isDevelopment()) error.printStackTrace();
            error.print();
        } catch (Exception e) {
            if (DebugMode.isDevelopment()) e.printStackTrace();
            SystemException.SYSTEM_ERROR.getThrow(e.getMessage()).print();
        }
    }

    // compile constructor
    private Main(File originFile, String compilePath) {
        fileCheck(originFile);
        if (!FileMode.OTL.check(originFile)) throw FileException.DO_NOT_SUPPORT.getThrow(originFile);
        if (compilePath == null || compilePath.isEmpty()) {
            String originPath = originFile.getAbsolutePath();
            compilePath = originPath
                    .substring(0, originPath.lastIndexOf('.'))
                    .concat(FileMode.OTLC.getExtension());
        } else if (!FileMode.OTLC.check(compilePath)) throw FileException.DO_NOT_SUPPORT.getThrow(compilePath);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(compilePath))) {
            oos.writeObject(readOrigin(originFile));
        } catch (FileNotFoundException e) {
            throw FileException.DO_NOT_PATH.getThrow(compilePath);
        } catch (IOException e) {
            throw FileException.CREATE_FILE_ERROR.getThrow(compilePath);
        }
    }

    // file run constructor
    private Main(File originFile) {
        fileCheck(originFile);
        (switch (FileMode.checkFile(originFile)) {
            case OTL -> readOrigin(originFile);
            case OTLC -> Main.readCompile(originFile);
        }).start();
    }

    // Shell run constructor
    private Main() {
        String line;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            int i = 0;
            ShellCodeItem SCI = new ShellCodeItem();
            Map<Integer, String> map = new HashMap<>();
            while (true) {
                writer.append(">>> ").flush();
                line = reader.readLine().strip();
                if (line.equals(SystemToken.QUIT)) break;
                map.put(i++, line);
                if (line.isEmpty() || CheckToken.startWith(line, Token.REMARK)) {
                    SCI.add(null, line);
                    continue;
                } else SCI.add(line);
                if (CheckToken.endWith(line, Token.LOOP_S)) {
                    int startLine = i-1;
                    int stack = 1;
                    do {
                        writer.append("--- ").flush();
                        line = reader.readLine().strip();
                        map.put(i++, line);
                        if (line.isEmpty()) {
                            SCI.add(null, line);
                            continue;
                        } else SCI.add(line);
                        if (CheckToken.startWith(line, Token.LOOP_E)) stack--;
                        if (CheckToken.endWith(line, Token.LOOP_S)) stack++;
                    } while (stack>0);
                    new ShellLineParser(map, SCI, startLine);
                    SCI.start(startLine, i);
                } else {
                    LineTool lineTool = LineParser.createParser(line);
                    SCI.add(lineTool, line);
                    lineTool.start(i);
                }
            }
        } catch (IOException e) {
            throw SystemException.SYSTEM_ERROR.getThrow(null);
        }
    }

    // file state check method
    private void fileCheck(File file) {
        if (!file.exists()) throw FileException.DO_NOT_PATH.getThrow(file.getPath());
        else if (!file.isFile()) throw FileException.FILE_TYPE_ERROR.getThrow(file.getPath());
        else if (!file.canRead()) throw FileException.DO_NOT_READ.getThrow(file.getPath());
    }

    // .otl file read
    private static CodeItem readCompile(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (CodeItem) ois.readObject();
        } catch (FileNotFoundException e) {
            throw FileException.DO_NOT_PATH.getThrow(file);
        } catch (IOException | ClassNotFoundException e) {
            throw SystemException.CREATE_ERROR.getThrow(file);
        }
    }

    // .otlc file read
    private CodeItem readOrigin(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String path = file.getPath();
            Map<Integer, String> map = new HashMap<>();
            int i=0; String line;
            while ((line=reader.readLine()) != null) map.put(i++, line.strip());
            LineParser lineParser = new LineParser(path, map);
            return new CodeItem(path, lineParser.getFILES(), lineParser.getLINES());
        } catch (FileNotFoundException e) {
            throw FileException.DO_NOT_PATH.getThrow(file);
        } catch (IOException e) {
            throw FileException.DO_NOT_READ.getThrow(file);
        }
    }
}
