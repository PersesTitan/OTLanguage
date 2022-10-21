package bin.apply.sys.make;

import bin.apply.sys.run.LoopBracket;
import bin.exception.FileException;
import bin.exception.MatchException;
import bin.token.LoopToken;
import bin.token.Token;

import java.io.File;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bracket implements LoopToken, Token {
    private final String endPattern = "(" + BLANK + orMerge(RETURN, PUTIN) + ")?";
    private final String loopStartPattern =
            orMerge(START, "\\n") + LINE_NUMBER + BLANK + orMerge(LOOP_SET) + "[^\\n]*\\{?(?=\\s*(\\n|$))";
    private final String loopEndPattern =
            orMerge(START, "\\n") + LINE_NUMBER + BLANK + MR + "(?=" + BLANK + endPattern + "\\s*(\\n|$))";
    private final Pattern pattern = Pattern.compile(orMerge(loopStartPattern, loopEndPattern));
    private final Matcher matcher = pattern.matcher("");
    private final Stack<Integer> stack = new Stack<>();

    public String bracket(String total, File file) {
        return bracket(total, file.getName(), true);
    }

    public String bracket(String total, String fileName, boolean fileCheck) {
        total = total.replaceAll("(^|\\n)" + LINE_NUMBER + "\\s*(?=(\\n|$))", "");
        String copy = total;

        if (fileCheck) {
            fileName = fileName.split(DOT, 2)[0];
            if (LOOP_TOKEN.containsKey(fileName)) throw FileException.alreadyExistsFileName();
            LOOP_TOKEN.put(fileName, total);
        }

        if (!(copy.contains("{") && copy.contains("}"))) return copy;
        stack.clear();
        matcher.reset(copy);
        while (matcher.find()) {
            String group = matcher.group().strip();
            if (Pattern.compile(MR + BLANK + END).matcher(group).find()) {
                if (stack.isEmpty()) {
                    StartLine.setError(group);
                    throw MatchException.bracketMatchError();
                } else if (stack.size() == 1) {
                    int start = stack.pop()+1;
                    int end = matcher.end()-1;
                    String oldValue = total.substring(start, end).strip();
                    int oldStart = getLineStart(oldValue);
                    int oldEnd = getLineEnd(oldValue);
                    String newValue = " (" + fileName + "," + (oldStart == 0 ? oldEnd : oldStart) + "," + oldEnd + ") ";
                    copy = copy.replace(total.substring(start-1, end+1), newValue);
                } else stack.pop();
            } else {
                if (!group.endsWith("{")) {
                    StartLine.setError(group);
                    throw MatchException.loopStyleError();
                } else stack.add(matcher.end()-1);
            }
        }

        if (!stack.isEmpty()) getErrorLine(total, stack.pop());
        return LoopBracket.deleteEnter(copy);
    }

    private void getErrorLine(String total, int pos) {
        String[] lines = total.substring(0, pos).split("\\n");
        String line = lines[lines.length-1];
        StartLine.setError(line);
        throw MatchException.bracketMatchError();
    }

    private int getLineStart(String total) {
        Matcher matcher = Pattern.compile(START + LINE_NUMBER).matcher(total);
        if (matcher.find()) return Integer.parseInt(matcher.group().trim());
        else return 0;
    }

    private int getLineEnd(String total) {
        Matcher matcher = Pattern.compile(LINE_NUMBER.trim() + END).matcher(total);
        if (matcher.find()) return Integer.parseInt(matcher.group().trim());
        else return total.length();
    }
}
