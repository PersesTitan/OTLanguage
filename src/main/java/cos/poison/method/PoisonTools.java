package cos.poison.method;

import bin.apply.Setting;
import bin.exception.FileException;
import bin.exception.VariableException;
import bin.token.LoopToken;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import static bin.apply.sys.item.Separator.SEPARATOR_FILE;

public interface PoisonTools extends LoopToken {
    default String[][] getParams(String[] lines) {
        if (lines.length == 0) return new String[0][0];
        String[][] params = new String[lines.length-1][3];
        Set<String> set = new HashSet<>();
        for (int i = 1; i<lines.length; i++) {
            String[] variable = matchSplitError(lines[i], BLANKS, 2); // ㅇㅅㅇ, ㅁ:ㅁ
            String[] value = matchSplitError(variable[1], VARIABLE_PUT, 2);
            if (set.contains(value[0])) throw VariableException.sameVariable();
            else set.add(value[0]);
            params[i-1][0] = variable[0];
            params[i-1][1] = value[0];
            params[i-1][2] = value[1];
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
