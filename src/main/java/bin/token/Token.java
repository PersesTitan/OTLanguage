package bin.token;

 public interface Token extends MergeToken {
    String START = "^";
    String END = "$";
    String BLANK = "\\s*";
    String BLANKS = "\\s+";

    // SING
    String STAR = "\\*";
    String PIPE = "\\|";
    String HYPHEN = "\\-";
    String PLUS = "\\+";
    String CARET = "\\^";
    String DOT = "\\.";
    String QUESTION = "\\?";
    String SL = "\\(";
    String SR = "\\)";
    String ML = "\\{";
    String MR = "\\}";
    String BL = "\\[";
    String BR = "\\]";
    String BACKSLASH = "\\\\";
    String PESO = "\\$";

    String NO_PATTERN = "(?<=(^|[^\\\\])(\\\\\\\\)?+)";
    String YES_PATTERN = "(?<=(^|[^\\\\])(\\\\\\\\)?+)\\\\";

    String COMMA = ",";
    String MAP_EQUAL = NO_PATTERN + "=";

    String ACCESS = "~";
    String LESS_SIGN = "<";
    String GREATER_SIGN = ">";
    String EXCLAMATION = "!";
    String AMPERSAND = "&";
    String REMARK = "#";
}
