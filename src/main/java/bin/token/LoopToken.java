package bin.token;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public interface LoopToken extends VariableToken {
    Map<String, String> LOOP_TOKEN = new HashMap<>();
    Set<String> LOOP_SET = new HashSet<>() {{
        add("[^\\^\\n]+" + FOR + "[^\\^\\n]+" + FOR + "[^\\^\\n]+");
        add(FOR + "[^\\^\\n]+" + FOR);
        add(IF); add(ELSE_IF);
        add(ELSE); add(WHITE);

        add(POISON + ACCESS + POISON_POST);
        add(POISON + ACCESS + POISON_GET);
        add(SERVER); add(TRY_CATCH);
        add(METHOD);
    }};

    String BRACE_STYLE = SL + FILE_TYPE + ",[0-9]+,[0-9]+" + SR;

    String ARGUMENT = BL + "[\\s\\S]*" + BR; // [...]

    String LINE_NUMBER = "[0-9]+ ";
    String RETURN_TOKEN = "=>";
    String RETURN = RETURN_TOKEN + BLANK + "\\S+";
    String PUTIN_TOKEN = "<=";
    String PUTIN = PUTIN_TOKEN + BLANK + "(" + String.join("|", TOTAL_LIST) + ")" + BLANKS + VARIABLE_NAME;

    String BREAK = "ㅂㅇㅂ";
    String CONTINUE = "ㅋㅇㅋ";
    String IF = QUESTION + "ㅅ" + QUESTION;
    String ELSE_IF = QUESTION + "ㅈ" + QUESTION;
    String ELSE = QUESTION + "ㅉ" + QUESTION;
    String TRY_CATCH = "ㅠㅅㅠ";
    String METHOD = "ㅁㅅㅁ";

    // LOOP
    String FOR = CARET;                 // ^
    String WHITE = PESO + "ㅅ" + PESO;

    // SERVER
    String SERVER = "ㅅㅂㅅ";

    // POISON
    String POISON = "ㅍㅇㅍ";
    String POISON_POST = "ㅍㅅㅍ";
    String POISON_GET = "ㄱㅅㄱ";
    String POISON_START = "ㅅㅌㅅ";
    String MODEL = "ㅁㄷㅁ";
    String REDIRECT = "";
    String GET_COOKIE = "";
    String SET_COOKIE = "";
}
