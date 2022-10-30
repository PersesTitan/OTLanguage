package cos.poison.method;

import bin.apply.Setting;
import bin.exception.FileException;
import bin.exception.VariableException;
import bin.token.LoopToken;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static bin.apply.sys.item.Separator.SEPARATOR_FILE;

public interface PoisonTools extends LoopToken {
    default String[][] getParams(String[] lines) {
        if (lines.length == 0) return new String[0][0];
        String[][] params = new String[lines.length][3];
        Set<String> set = new HashSet<>();
        for (int i = 0; i<params.length; i++) {
            String[] value = matchSplitError(lines[i], "(" + BLANKS + "|" + VARIABLE_PUT + ")", 3);
            if (set.contains(value[1])) throw VariableException.sameVariable();
            else set.add(value[1]);
            params[i][0] = value[0];
            params[i][1] = value[1];
            params[i][2] = value[2];
        }
        return params;
    }

    default String[] getTotal(String line) {
        return matchSplitError(bothEndCut(line), ",", 3);
    }

    default String getHtml(String line) {
        String url = Setting.path + SEPARATOR_FILE + line.replace(ACCESS, SEPARATOR_FILE);
        File file = new File(url);
        if (!file.exists()) throw FileException.noFindError();
        else if (!file.isFile()) throw FileException.isNotFileError();
        return url;
    }
}
