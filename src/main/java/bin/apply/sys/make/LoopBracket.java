package bin.apply.sys.make;

import static bin.token.LoopToken.*;

public interface LoopBracket {
    String BLANK = "(\\s?|\\s+)";
    String LINE = "\\n" + LINE_NUMBER + BLANK;
    String IfPattern = "(^|\\n)" + LINE_NUMBER + BLANK + IF + "[^\\n]+" + BRACE_STYLE;
    String Else_IfPattern = ELSE_IF + "[^\\n]+" + BRACE_STYLE;
    String ElsePattern = ELSE + BLANK + BRACE_STYLE;
    String ElsePatternNo = "(" + ElsePattern + ")?";
    String Else_IfPatterns = "(" + Else_IfPattern + ")+" + ElsePatternNo;
    String pattern1 = "(?!" + LINE + Else_IfPattern + ")" + LINE + "(?=" + ElsePattern + ")";
    String pattern2 = "(?!" + LINE + Else_IfPattern + ")" + LINE + "(?=" + Else_IfPatterns + ")";
    String pattern3 = "(?!" + IfPattern + ")" + LINE + "(?=" + ElsePattern + ")";
    String pattern4 = "(?!" + IfPattern + ")" + LINE + "(?=" + Else_IfPatterns + ")";

    default boolean check(String total) {
        return total.contains(ELSE_IF_) || total.contains(ELSE_);
    }

    default String deleteEnter(String total) {
        return total
                .replaceAll(pattern1, " ")
                .replaceAll(pattern2, " ")
                .replaceAll(pattern3, " ")
                .replaceAll(pattern4, " ");
    }
}
