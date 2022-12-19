package v4.bin.apply;

import v4.bin.apply.sys.item.TypeMap;
import v4.start.Return;
import v4.start.Start;

import java.util.LinkedList;

import static bin.apply.Setting.runMessage;
import static bin.token.Token.REMARK;
import static bin.token.VariableToken.VARIABLE_GET_E;
import static bin.token.VariableToken.VARIABLE_GET_S;

public class Setting {
    public static void start(String line, String errorLine, LinkedList<TypeMap> repositoryArray) {
        if (line.isBlank() || line.startsWith(REMARK)) return;

        if (Start.start(line, true, repositoryArray)) return;
        line = lineStart(line, repositoryArray);
        if (Start.start(line, false, repositoryArray)) return;
        runMessage(errorLine);

//        if (For.getInstance().check(line)) {For.getInstance().start(line, repositoryArray);return;}
    }

    public static String lineStart(String line, LinkedList<TypeMap> repositoryArray) {
        if (line.contains(VARIABLE_GET_S) && line.contains(VARIABLE_GET_E))
            line = Return.start(line, repositoryArray);
        return line;
    }
}
