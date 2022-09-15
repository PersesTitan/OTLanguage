package bin.token;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public interface LoopToken extends VariableToken {
    Map<String, String> LOOP_TOKEN = new HashMap<>();
    Set<String> LOOP_SET = new HashSet<>() {{
        add("[\\s\\S]+" + FOR + "[\\s\\S]+" + FOR + "[\\s\\S]+");
        add(IF); add(ELSE_IF);
        add(ELSE); add(SERVER);
    }};

    default String BRACE_STYLE() {
//        return SL + "(" + String.join("|", LOOP_TOKEN.keySet()) + "),[0-9]+,[0-9]+" + SR;
        return SL + FILE_TYPE + ",[0-9]+,[0-9]+" + SR;
    }

    String LINE_NUMBER = "[0-9]+ ";
    String RETURN = "=>" + VARIABLE_NAME;
    String PUTIN_TOKEN = "<=";
    String PUTIN = PUTIN_TOKEN + "(" + String.join("|", TOTAL_LIST) + ")" + BLANKS + VARIABLE_NAME;

    String BREAK = "ㅂㅇㅂ";
    String CONTINUE = "ㅋㅇㅋ";
    String IF = QUESTION + "ㅅ" + QUESTION;
    String ELSE_IF = QUESTION + "ㅈ" + QUESTION;
    String ELSE = QUESTION + "ㅉ" + QUESTION;

    String FOR = CARET; // ^

    // SERVER
    String SERVER = "ㅅㅂㅅ";
}
