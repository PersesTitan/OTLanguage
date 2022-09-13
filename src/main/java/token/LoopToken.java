package token;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public interface LoopToken extends VariableToken {
    Map<String, String> LOOP_TOKEN = new HashMap<>();
    Set<String> LOOP_SET = new HashSet<>() {{
        add(IF); add(ELSE_IF); add(ELSE);
    }};

    default String BRACE_STYLE() {
        return SL + "(" + String.join("|", LOOP_TOKEN.keySet()) + "),[0-9]+,[0-9]+" + SR;
    }

    String LINE_NUMBER = "[0-9]+ ";

    String IF = QUESTION + "ㅅ" + QUESTION;
    String ELSE_IF = QUESTION + "ㅈ" + QUESTION;
    String ELSE = QUESTION + "ㅉ" + QUESTION;
}
