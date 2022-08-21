package origin.loop.define;

import event.Setting;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import origin.exception.MatchException;
import origin.exception.MatchMessage;
import origin.loop.model.LoopWork;
import origin.variable.model.Repository;

import java.util.Stack;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bracket implements Repository {

    private final static char left = '{';
    private final static char right = '}';

    private String deleteEnter(String total) {
        for (LoopWork loop : loopWorks) total = setMatcher(total, loop.getPattern());
        return total;
    }

    //괄호가 존재하면 아이디로 대체
    public String bracket(String total) {
        Stack<Integer> stack = new Stack<>();
        total = deleteEnter(total);

        for (int i = 0; i < total.length(); i++) {
            if (total.charAt(i) == left) {
                stack.add(i);
            }

            if (total.charAt(i) == right) {
                if (stack.empty()) throw new MatchException(MatchMessage.matchError);
                String uuid = UUID.randomUUID().toString();
                int start = stack.pop();
                String bl = total.substring(start, i+1);

                total = total.replace(bl, " " + uuid.concat("\n"));
                total = delete(total); // uuid\n=>변수명 -> uuid=>변수명으로 변경
                i = start + uuid.length();
                uuidMap.put(uuid, bl.strip().substring(1, bl.length()-1));
            }
        }

        total = customEnterDelete(total);
        return ifDelete(total);
    }

    private String setMatcher(String total, String text) {
        String patternText = text + "\\s*\\n+\\s*\\" + left;
        Pattern pattern = Pattern.compile(patternText);
        Matcher matcher = pattern.matcher(total);

        //?ㅅ?
        Pattern p = Pattern.compile(text);
        while (matcher.find()) {
            String group = matcher.group();
            Matcher m = p.matcher(group);
            if (m.find()) {
                String change = m.group().strip().concat(Character.toString(left)) + "\n";
                total = total.replace(group, change);
            }
        }

        return total;
    }

    //uuid 가 존재하는지 체크함
    public boolean check(String line) {
        if (uuidMap.isEmpty()) return false;
        return uuidMap.entrySet()
                .stream()
                .anyMatch(v -> line.contains(v.getKey()));
    }

    // uuid\n=>변수명 -> uuid 변수명
    public String delete(String total) {
        String patternText = "(\\n\\s*)+=>\\s*~*"+ Setting.variableStyle;
        Matcher matcher = Pattern.compile(patternText).matcher(total);
        while (matcher.find()) {
            String group = matcher.group().strip();
            total = total.replaceFirst(patternText, group+"\n");
        }
        return total;
    }

    // ?ㅅ? ㅇㅇ uuid ?ㅈ? ㅇㅇ uuid
    private String ifDelete(String total) {
        String patternText = "\\n+\\s*(\\?ㅈ\\?|\\?ㅉ\\?)";
        Matcher matcher = Pattern.compile(patternText).matcher(total);
        while (matcher.find())
            total = total.replaceFirst(patternText, " " + matcher.group().strip() + " ");
        return total;
    }

    // ((\n)) => [ ]
    private String customEnterDelete(String total) {
        return total.replaceAll("\\(\\((\\s*\\n\\s*)+\\)\\)", "");
    }
}
