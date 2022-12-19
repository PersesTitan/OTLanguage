package bin.token;

import bin.apply.Setting;
import bin.exception.FileException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import static bin.apply.Setting.debugMode;
import static bin.apply.sys.compile.Decompile.isDecompile;
import static bin.apply.sys.item.Separator.SYSTEM_PATH;
import static bin.apply.sys.item.Separator.isWindow;
import static bin.apply.sys.make.StartLine.developmentMode;
import static bin.token.StringToken.SPLIT;
import static bin.token.StringToken.SPLIT_REGULAR;
import static java.nio.charset.StandardCharsets.*;

public interface LoopToken extends VariableToken {
    Map<String, String> LOOP_TOKEN = new HashMap<>();
    Set<String> LOOP_SET = new HashSet<>() {{
        // 디컴파일 파일이 아닐때
        if (!isDecompile) {
            try (BufferedReader br = new BufferedReader(new FileReader(SYSTEM_PATH, UTF_8))) {
                br.lines().filter(Predicate.not(String::isBlank))
                        .map(String::strip)
                        .forEach(this::add);
            } catch (IOException i) {
                if (developmentMode) i.printStackTrace();
                new FileException().printErrorMessage(new FileException().didNotReadSystemFile(), Setting.mainPath);
                // 운영체제가 윈도우가 아니거나 디버깅모드가 컴파일이 아닐때 종료 허용
                if (!isWindow || debugMode.isNoCompile()) System.exit(0);
            }
        }
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
    String KLASS = "ㅋㅅㅋ";
    String IF_ = "?ㅅ?";
    String ELSE_IF_ = "?ㅈ?";
    String ELSE_ = "?ㅉ?";

    // SHORT LOOP
    String FILE_LINE_FOR = "$";
    String STRING_CHAR_FOR = "$";
    String SPLIT_FOR = SPLIT + "$";
    String SPLIT_REGULAR_FOR = SPLIT_REGULAR + "$";

    // LOOP
    String FOR = CARET;                 // ^
    String WHITE = PESO + "ㅅ" + PESO;
    String WHITE_ = "$ㅅ$";

    // SERVER
    String SERVER = "ㅅㅂㅅ";

    // POISON
    String POISON = "ㅍㅇㅍ";
    String POISON_POST = "ㅍㅅㅍ";
    String POISON_GET = "ㄱㅅㄱ";
    String POISON_START = "ㅅㅌㅅ";
    String MODEL = "ㅁㄷㅁ";
    String REDIRECT = "ㄹㄷㄹ";
    String COOKIE = "ㄱㅋㄱ";
    String GET_COOKIE = COOKIE;
    String SET_COOKIE = COOKIE;
    String ISEMPTY_COOKIE = COOKIE + QUESTION_S;
    String DELETE_COOKIE = COOKIE + HYPHEN_;
    String GET_URL = "ㅇㄹㅇ";

    // SHELL
    String SHELL = "ㅉㅂㅉ";
    String SHELL_START = "ㅅㅌㅅ";
}
