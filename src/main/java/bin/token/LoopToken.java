package bin.token;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public interface LoopToken extends VariableToken {
    Map<String, String> LOOP_TOKEN = new HashMap<>();
    Set<String> LOOP_SET = new HashSet<>() {{
        add("[^\\^\\n]+" + FOR + "[^\\^\\n]+" + FOR + "[^\\^\\n]+");
        add(IF); add(ELSE_IF);
        add(ELSE); add(WHITE);

        add(POISON + POISON_POST);
        add(POISON + POISON_GET);
        add(SERVER);
    }};

    default String BRACE_STYLE() {
//        return SL + "(" + String.join("|", LOOP_TOKEN.keySet()) + "),[0-9]+,[0-9]+" + SR;
        return SL + FILE_TYPE + ",[0-9]+,[0-9]+" + SR;
    }

    String ARGUMENT = BL + "[^" + BL + BR + "]+" + BR; // [...]

    String LINE_NUMBER = "[0-9]+ ";
    String RETURN = "=>" + BLANK + "\\S+";
    String PUTIN_TOKEN = "<=";
    String PUTIN = PUTIN_TOKEN + "(" + String.join("|", TOTAL_LIST) + ")" + BLANKS + VARIABLE_NAME;

    String BREAK = "ㅂㅇㅂ";
    String CONTINUE = "ㅋㅇㅋ";
    String IF = QUESTION + "ㅅ" + QUESTION;
    String ELSE_IF = QUESTION + "ㅈ" + QUESTION;
    String ELSE = QUESTION + "ㅉ" + QUESTION;

    String FOR = CARET; // ^
    String WHITE = PESO + "ㅅ" + PESO;

    // SERVER
    String SERVER = "ㅅㅂㅅ";

    // POISON
    String POISON = "ㅍㅇㅍ";
    String POISON_POST = "ㅍㅅㅍ";
    String POISON_GET = "ㄱㅅㄱ";
    String POISON_START = "ㅅㅌㅅ";
}
