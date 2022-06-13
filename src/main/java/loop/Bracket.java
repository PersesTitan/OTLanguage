package loop;

import item.Setting;

import java.util.Stack;
import java.util.UUID;
import java.util.regex.Pattern;

public class Bracket extends Setting {
    private final String bracketR = "}";
    private final String bracketL = "\\{";
    private final Pattern patternR = Pattern.compile(bracketR);
    private final Pattern patternL = Pattern.compile(bracketL);
    Stack<Integer> stack = new Stack<>();

    //체크
    private boolean checkR(String line) {
        return patternR.matcher(line).find();
    }

    private boolean checkL(String line) {
        return patternL.matcher(line).find();
    }

    //공백
    public String bracket(String total) throws Exception {
        stack.clear();
        for (int i = 0; i < total.length(); i++) {
            if (total.charAt(i) == '{') {
                stack.add(i);
            }

            if (total.charAt(i) == '}') {
                if (stack.empty()) throw new Exception("괄호 짝이 맞지 않습니다.");
                String uuid = UUID.randomUUID().toString();
                int start = stack.pop();
                String bl = total.substring(start, i);

                total = total.replace(bl, uuid);
                uuidMap.put(uuid, bl);
                if (total.contains("{") && total.contains("}"))
                    return bracket(total);
            }
        }
        return total;
    }
}
