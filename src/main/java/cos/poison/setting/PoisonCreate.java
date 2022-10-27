package cos.poison.setting;

import bin.exception.VariableException;
import bin.token.LoopToken;
import work.StartWork;

import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Pattern;

import static bin.check.VariableCheck.isInteger;
import static cos.poison.Poison.httpServerManager;

public class PoisonCreate implements LoopToken, StartWork {
    private final String className;
    private final Pattern pattern;

    public PoisonCreate(String className) {
        this.className = className;
        String pt1 = merge(BL, "[^", BL, BR, "]+", BR, BL, "\\d+", BR);
        String pt2 = merge(BLANKS, "[^", BL, BR, "]+");
        String pt3 = merge(BLANKS, "\\d+");
        String patternText = startEndMerge(className, orMerge(pt1, pt2, pt3), "?");
        this.pattern = Pattern.compile(patternText);
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line, String origen,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        line = line.strip();
        if (line.equals(className)) httpServerManager.createServer();
        else if (line.startsWith(className + "[")) {
            String[] tokens =
                    matchSplitError(bothEndCut(line.replaceFirst(START + className, "")),
                            BR + BL, 2);
            String ports = tokens[1].strip();
            if (!isInteger(ports)) throw VariableException.typeMatch();
            String host = tokens[0].strip();
            int port = Integer.parseInt(ports);
            httpServerManager.createServer(host, port);
        } else {
            // url or port
            String tokens = matchSplitError(line, BLANKS, 2)[1];
            if (isInteger(tokens)) httpServerManager.createServer(Integer.parseInt(tokens));
            else httpServerManager.createServer(tokens);
        }
    }

    @Override
    public void first() {

    }
}
