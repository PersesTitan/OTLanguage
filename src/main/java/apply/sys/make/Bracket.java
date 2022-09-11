package apply.sys.make;

import exception.FileException;
import exception.MatchException;
import token.LoopToken;

import java.io.File;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bracket implements LoopToken {
    private static final String patternText = START + LINE_NUMBER + BLANK + "(" + String.join("|", LOOP_SET) + ")";
    private static final Pattern pattern = Pattern.compile(patternText);
    private static final char left = '{';
    private static final char right = '}';

    public String bracket(String total, File file) {
        return bracket(total, file.getName(), true);
    }

    public String bracket(String total, String fileName, boolean fileCheck) {
        total = total.replaceAll("(^|\\n)" + LINE_NUMBER + "\\s*(?=\\n)", "");
        String copy = total;
        if (fileCheck) {
            fileName = fileName.split(DOT, 2)[0];
            if (LOOP_TOKEN.containsKey(fileName)) throw FileException.alreadyExistsFileName();
            LOOP_TOKEN.put(fileName, total);
        } else if (!(copy.contains(Character.toString(left)) && copy.contains(Character.toString(right))))
            return returnCopy(copy);

        int count = 0;
        Stack<Integer> stack = new Stack<>();
        for(int i = 0; i<total.length(); i++) {
            char c = total.charAt(i);
            if (c == left) {
                if (check(total, i)) stack.add(i);
                else count++;
            } else if (c == right) {
                if (count != 0) count--;
                else if (stack.isEmpty()) {
                    StartLine.setError(getLine(total, i), total);
                    throw MatchException.bracketMatchError();
                } else {
                    int start = stack.pop();
                    String oldWord = total.substring(start, i+1);
                    String newWord = " (" + fileName + "," + (start+1) + "," + i + ") ";
                    copy = copy.replace(oldWord, newWord);
                }
            }
        }

        if (!stack.isEmpty()) {
            StartLine.setError(getLine(total, stack.pop()), total);
            throw MatchException.bracketMatchError();
        } else return returnCopy(copy);
    }

    private String returnCopy(String copy) {
        return cutIF(copy.strip()
                .replaceAll("(\\n|^)" + LINE_NUMBER + "(?=\\s*" + BRACE_STYLE() + "\\s*\\n)", "")
                .strip());
    }

    private boolean check(String total, int position) {
        return pattern.matcher(getLine(total, position)).find();
    }

    private String getLine(String total, int position) {
        String line = total.substring(0, position);
        String patternText = "\\n[0-9]+\\s*$";
        Matcher matcher = Pattern.compile(patternText).matcher(line);
        while (matcher.find()) {
            line = line.replaceFirst(patternText, "");
            matcher.reset(line);
        }
        String[] values = line.split("\\n");
        return values[values.length-1];
    }

    // IF, ELSE IF, ELSE
    private String cutIF(String total) {
        String IFPattern = blacksMerge("(\\n|^)" + LINE_NUMBER + "(\\s?|\\s?+)" + IF, "[\\s\\S]?+" + BRACE_STYLE());
//        String ELSE_IFPattern1 = blacksMerge("\\n" + LINE_NUMBER + "(\\s?|\\s?+)" + ELSE_IF, "[\\s\\S]?+" + BRACE_STYLE());
        String ELSE_IFPattern = blacksMerge(ELSE_IF, "[\\s\\S]?+" + BRACE_STYLE());
        String ELSEPattern = blacksMerge(ELSE, "\\s*" + BRACE_STYLE());
        String patternText =
                "(?<!" + orMerge(IFPattern, ELSE_IFPattern) + ")" +
                "\\n" + LINE_NUMBER + "\\s*" +
                "(?=" + orMerge(ELSE_IFPattern, ELSEPattern) + "[\\s\\S]+)";

        return total.replaceAll(patternText, "");
    }
}
