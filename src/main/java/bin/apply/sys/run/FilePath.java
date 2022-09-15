package bin.apply.sys.run;

import bin.apply.sys.item.Separator;
import bin.exception.FileException;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.LoopToken;
import bin.token.Token;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import static bin.apply.Controller.bracket;
import static bin.apply.sys.item.SystemSetting.extension;

public class FilePath implements Token, LoopToken {
    public final Pattern pattern =
            Pattern.compile(START + BLANK + FILE + BLANKS + FILE_TYPE + "(" + ACCESS + FILE_TYPE + ")" + BLANK + END);

    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    public void start(String line) {
        String path = line
                .strip()
                .replaceFirst(START + FILE + BLANKS, "")
                .replace(ACCESS, Separator.SEPARATOR_FILE);
        for (String ext : extension) {
            File file = new File(path + ext);
            if (file.isFile()) {
                importFile(file);
                return;
            }
        }
        throw FileException.pathNoHaveError();
    }

    private void importFile(File file) {
        if (!file.canRead()) throw FileException.noReadError();
        String text = "";
        long count = 0;
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath(), StandardCharsets.UTF_8))) {
            while ((text = reader.readLine()) != null) builder.append(++count).append(" ").append(text).append("\n");
            bracket.bracket(builder.toString(), file);
        } catch (VariableException e) {
            VariableException.variableErrorMessage(e, file.getAbsolutePath(), text, count);
        } catch (MatchException e) {
            MatchException.matchErrorMessage(e, file.getAbsolutePath(), text, count);
        } catch (FileException e) {
            FileException.printErrorMessage(e, file.getAbsolutePath(), text, count);
        } catch (IOException ignored) {}
    }

}
