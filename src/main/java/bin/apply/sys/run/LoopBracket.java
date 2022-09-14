package bin.apply.sys.run;

import bin.token.LoopToken;
import bin.token.Token;

public class LoopBracket implements LoopToken, Token {
    private final String BRACE_STYLE = BRACE_STYLE();

    private final String BLANK = "(\\s?|\\s+)";
    private final String LINE = "\\n" + LINE_NUMBER + BLANK;
    private final String IfPattern = "(^|\\n)" + LINE_NUMBER + BLANK + IF + "[^\\n]+" + BRACE_STYLE;
    private final String Else_IfPattern = ELSE_IF + "[^\\n]+" + BRACE_STYLE;
    private final String ElsePattern = ELSE + BLANK + BLANK + BRACE_STYLE;

    private final String ElsePatternNo = "(" + ElsePattern + ")?";
    private final String Else_IfPatterns = "(" + Else_IfPattern + ")+" + ElsePatternNo;

    private final String pattern1 = "(?<=" + LINE + Else_IfPattern + ")" + LINE + "(?=" + ElsePattern + ")";
    private final String pattern2 = "(?<=" + LINE + Else_IfPattern + ")" + LINE + "(?=" + Else_IfPatterns + ")";
    private final String pattern3 = "(?<=" + IfPattern + ")" + LINE + "(?=" + ElsePattern + ")";
    private final String pattern4 = "(?<=" + IfPattern + ")" + LINE + "(?=" + Else_IfPatterns + ")";

    public String deleteEnter(String total) {
        return total
                .replaceAll(pattern1, " ")
                .replaceAll(pattern2, " ")
                .replaceAll(pattern3, " ")
                .replaceAll(pattern4, " ");
    }
}
