package bin.apply.sys.run;

import bin.token.LoopToken;

public class LoopBracket implements LoopToken {
    private static final String BLANK = "(\\s?|\\s+)";
    private static final String LINE = "\\n" + LINE_NUMBER + BLANK;
    private static final String IfPattern = "(^|\\n)" + LINE_NUMBER + BLANK + IF + "[^\\n]+" + BRACE_STYLE;
    private static final String Else_IfPattern = ELSE_IF + "[^\\n]+" + BRACE_STYLE;
    private static final String ElsePattern = ELSE + BLANK + BRACE_STYLE;

    private static final String ElsePatternNo = "(" + ElsePattern + ")?";
    private static final String Else_IfPatterns = "(" + Else_IfPattern + ")+" + ElsePatternNo;

    private static final String pattern1 = "(?!" + LINE + Else_IfPattern + ")" + LINE + "(?=" + ElsePattern + ")";
    private static final String pattern2 = "(?!" + LINE + Else_IfPattern + ")" + LINE + "(?=" + Else_IfPatterns + ")";
    private static final String pattern3 = "(?!" + IfPattern + ")" + LINE + "(?=" + ElsePattern + ")";
    private static final String pattern4 = "(?!" + IfPattern + ")" + LINE + "(?=" + Else_IfPatterns + ")";

    public static String deleteEnter(String total) {
        return total
                .replaceAll(pattern1, " ")
                .replaceAll(pattern2, " ")
                .replaceAll(pattern3, " ")
                .replaceAll(pattern4, " ");
    }
}
