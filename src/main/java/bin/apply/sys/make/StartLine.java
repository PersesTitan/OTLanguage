package bin.apply.sys.make;

import bin.apply.Setting;
import bin.apply.sys.item.RunType;
import bin.exception.ConsoleException;
import bin.exception.MatchException;
import bin.exception.ServerException;
import bin.exception.VariableException;
import bin.token.LoopToken;

import java.io.File;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

import static bin.apply.Controller.bracket;
import static bin.apply.Controller.loopController;
import static bin.apply.Repository.*;
import static bin.apply.Setting.lineStart;
import static bin.apply.sys.item.SystemSetting.extensionCheck;

public class StartLine implements LoopToken {

    @SafeVarargs
    public static void startLine(String total, String path,
                                 Map<String, Map<String, Object>>... repository) {
        boolean extensionCheck = extensionCheck(path);
        if (extensionCheck) errorPath.set(path);
        try {
            String finalTotal = getFinalTotal(extensionCheck, total, path);
            startStartLine(finalTotal, total, repository);
        } catch (VariableException e) {
            VariableException.variableErrorMessage(e, errorPath.get(), errorLine.get(), errorCount.get());
            setLine();
        } catch (MatchException e) {
            MatchException.matchErrorMessage(e, errorPath.get(), errorLine.get(), errorCount.get());
            setLine();
        } catch (ServerException e) {
            ServerException.serverErrorMessage(e, errorPath.get(), errorLine.get(), errorCount.get());
            setLine();
        } catch (ConsoleException e) {
            ConsoleException.consoleErrorMessage(e, errorPath.get(), errorLine.get(), errorCount.get());
            setLine();
        }
    }

    private static void setLine() {
        if (Setting.runType.equals(RunType.Normal)) System.exit(0);
    }

    public static String getFinalTotal(boolean extensionCheck, String total, String path) {
        return extensionCheck
                ? bracket.bracket(total, new File(path))
                : bracket.bracket(total, path, false);
    }

    @SafeVarargs
    public static void startStartLine(String finalTotal, String total,
                                      Map<String, Map<String, Object>>... repository) {
        finalTotal.lines()
                .filter(Predicate.not(String::isBlank))
                .map(v -> setError(v, total))
                .forEach(line -> Setting.start(line, errorLine.get(), repository));
    }

    @SafeVarargs
    public static String startLoop(String total, String fileName,
                                 Map<String, Map<String, Object>>... repository) {
        for (var line : bracket.bracket(total, fileName, false).lines().toList()) {
            line = loopController.check(setError(line, total).strip());
            if (line.equals(BREAK) || line.equals(CONTINUE)) return line;
            else Setting.start(line, errorLine.get(), repository);
        }
        return "FINISH";
    }

    @SafeVarargs
    public static void startPoison(String total, String fileName, Map<String, Map<String, Object>>...repository) {
        CONTINUE:
        for (var line : bracket.bracket(total, fileName, false).lines().toList()) {
            if (line.isBlank()) continue;
            final String origen = (line = setError(line, total));
            final String value = new StringTokenizer(line).nextToken();

            if (priorityWorkMap.containsKey(value)) {priorityWorkMap.get(value).start(line, origen, repository);continue;}
            for (var work : priorityWorks) {if (work.check(line)) {work.start(line, origen, repository); continue CONTINUE;}}
            line = lineStart(line, repository);

            if (startWorkMap.containsKey(value)) {startWorkMap.get(value).start(line, origen, repository);continue;}
            for (var work : startWorks) {if (work.check(line)) {work.start(line, origen, repository);continue CONTINUE;}}

            Setting.runMessage(origen);
        }

    }

    public static final AtomicLong errorCount = new AtomicLong(0);
    public static final AtomicReference<String> errorLine = new AtomicReference<>("");
    public static final AtomicReference<String> errorPath = new AtomicReference<>();
    public static String setError(String line, String total) {
        if (line.isBlank()) return "";
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
