package bin.apply.sys.make;

import bin.apply.Repository;
import bin.apply.Setting;
import bin.apply.sys.item.RunType;
import bin.calculator.tool.Calculator;
import bin.exception.*;
import bin.token.LoopToken;

import java.io.File;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

import static bin.apply.Controller.bracket;
import static bin.apply.Controller.loopController;
import static bin.apply.Setting.start;
import static bin.apply.sys.item.SystemSetting.extensionCheck;
import static bin.calculator.tool.Calculator.*;

public class StartLine implements LoopToken, Calculator {
    public static boolean developmentMode = false;
    public static void startLine(String total, String path,
                                 LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        boolean extensionCheck = extensionCheck(path);
        if (extensionCheck) errorPath.set(path);
        try {
            String finalTotal = getFinalTotal(extensionCheck, total, path);
            startStartLine(finalTotal, total, repositoryArray);
        } catch (VariableException e) {
            new VariableException().variableErrorMessage(e, errorPath.get(), errorLine.get(), errorCount.get());
            setLine(e);
        } catch (MatchException e) {
            new MatchException().matchErrorMessage(e, errorPath.get(), errorLine.get(), errorCount.get());
            setLine(e);
        } catch (ServerException e) {
            new ServerException().serverErrorMessage(e, errorPath.get(), errorLine.get(), errorCount.get());
            setLine(e);
        } catch (ConsoleException e) {
            new ConsoleException().consoleErrorMessage(e, errorPath.get(), errorLine.get(), errorCount.get());
            setLine(e);
        } catch (CosException e) {
            new CosException().cosErrorMessage(e, errorPath.get(), errorLine.get(), errorCount.get());
            setLine(e);
        }
    }

    private static void setLine(RuntimeException e) {
        if (developmentMode) e.printStackTrace();
        if (Setting.runType.equals(RunType.Normal)) System.exit(0);
    }

    public static String getFinalTotal(boolean extensionCheck, String total, String path) {
        return extensionCheck
                ? bracket.bracket(total, new File(path))
                : bracket.bracket(total, path, false);
    }

    public static void startStartLine(String finalTotal, String total,
                                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        finalTotal.lines()
                .filter(Predicate.not(String::isBlank))
                .map(v -> setError(v, total))
                .forEach(line -> Setting.start(line, errorLine.get(), repositoryArray));
    }

    public static String startLoop(String total, String fileName,
                                   LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        for (var line : bracket.bracket(total, fileName, false).lines().toList()) {
            line = loopController.check(setError(line, total).strip());
            if (line.endsWith(BREAK) || line.endsWith(CONTINUE)) {
                // FINISH, CONTINUE, BREAK
                String lines = returnLoop(line.strip(), repositoryArray);
                if (line.equals(CONTINUE)) continue;
                if (!lines.equals(finish)) return lines;
            } else Setting.start(line, errorLine.get(), repositoryArray);
        }
        return finish;
    }

    private final static String finish = "FINISH";
    private static String returnLoop(String line, LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (line.equals(BREAK)) return BREAK;
        else if (line.equals(CONTINUE)) return CONTINUE;
        else if (line.endsWith(BREAK)) {
            int position = line.lastIndexOf('?');
            return line.substring(position + 1).strip().equals(BREAK)
                    && getBool(line.substring(0, position), repositoryArray)
                    ? BREAK
                    : finish;
        } else if (line.endsWith(CONTINUE)) {
            int position = line.lastIndexOf('?');
            return line.substring(position + 1).strip().equals(CONTINUE)
                    && getBool(line.substring(0, position), repositoryArray)
                    ? CONTINUE
                    : finish;
        }
        return "";
    }

    public static void startPoison(String total, String fileName,
                                   LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        for (var line : bracket.bracket(total, fileName, false).lines().toList()) {
            if ((line = setError(line, total)).isBlank()) continue;
            start(line, errorLine.get(), repositoryArray);
        }
    }

    public static final AtomicLong errorCount = new AtomicLong(0);
    public static final AtomicReference<String> errorLine = new AtomicReference<>("");
    public static final AtomicReference<String> errorPath = new AtomicReference<>();
    public static String setError(String line, String total) {
        if (line.isBlank()) return "";
        else if (LOOP_TOKEN.containsKey(total)) total = LOOP_TOKEN.get(total);
        String[] tokens = line.split(" ", 2);
        String lineCount = tokens[0];
        errorCount.set(Integer.parseInt(lineCount));
        if (tokens.length == 2) {
            int start = total.indexOf("\n" + lineCount + " ") + 1;
            int end = total.indexOf("\n", start);
            if (end == -1) end = total.length();
            String lines = total.substring(start + lineCount.length() + 1, end);
            errorLine.set(lines);
            return tokens[1];
        } else return "";
    }
}
