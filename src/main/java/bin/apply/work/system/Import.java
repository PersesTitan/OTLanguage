package bin.apply.work.system;

import bin.apply.item.CodeItem;
import bin.exception.FileException;
import bin.token.EditToken;
import bin.token.KlassToken;
import bin.token.ParserToken;
import bin.token.check.CheckToken;
import work.ResetWork;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public record Import(String module) {
    public Import {
        try {
            CodeItem.addModule(module);
            ((ResetWork) Class.forName(String.format("cos.%s.Reset", module)).getConstructor().newInstance()).reset();
        } catch (ClassNotFoundException | InvocationTargetException |
                 InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw FileException.ADD_FAIL_ERROR.getThrow(module);
        }
    }

    public static String readImport(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            Set<String> MODULES = new HashSet<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = ParserToken.paramParser(line);
                if (tokens.length == 2 && Objects.equals(tokens[0], KlassToken.IMPORT)) {
                    if (CheckToken.isParams(tokens[1])) MODULES.add(EditToken.bothCut(tokens[1]));
                    else MODULES.add(tokens[1].strip());
                }
            }
            return String.join(" ", MODULES);
        } catch (FileNotFoundException e) {
            throw FileException.DO_NOT_PATH.getThrow(file);
        } catch (IOException e) {
            throw FileException.DO_NOT_READ.getThrow(file);
        }
    }
}
