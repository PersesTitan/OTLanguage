package cos.poison.root;

import bin.token.MergeToken;

public class IfHTML implements MergeToken {
    private IfHTML() {}
    private static IfHTML ifHTML;
    public static IfHTML getInstance() {
        if (ifHTML == null) {
            synchronized (IfHTML.class) {
                ifHTML = new IfHTML();
            }
        }
        return ifHTML;
    }

    private final String startIf = "{?";
    private final String endIf = "?}";
    private final String ELSE = "{!!}";
    public boolean check(String htmlTotal) {
        return htmlTotal.contains(startIf)
                && htmlTotal.contains(endIf)
                && htmlTotal.contains(ELSE);
    }

    public String replace(String htmlTotal) {
        StringBuilder total = new StringBuilder(htmlTotal);
        int i;
        try {
            while ((i = total.lastIndexOf(startIf)) != -1) {
                int end = total.indexOf(ELSE, i);
                total.replace(i, end + ELSE.length(), cut(total.substring(i, end + ELSE.length())));
            }
        } catch (StringIndexOutOfBoundsException ignored) {}
        return total.toString();
    }

    // {? test ?} ... {!!} => ... or [ ]
    private String cut(String line) {
        int start = line.indexOf(endIf);
        String token = line.substring(0, start + endIf.length());               // {? test ?}
        token = bothEndCut(token, startIf.length(), endIf.length()).strip();    // test
        return VariableHTML.getInstance().map.containsKey(token)
                ? cutIf(line, start)
                : cutElse(line);
    }

    private final String Else = "{=??=}";
    private String cutElse(String line) {
        int i = line.indexOf(Else);
        return i == -1
                ? ""
                : line.substring(i + Else.length(), line.length() - ELSE.length());
    }

    private String cutIf(String line, int start) {
        int i = line.indexOf(Else);
        return i == -1
                ? line.substring(start + endIf.length(), line.length() - ELSE.length())
                : line.substring(start + endIf.length(), i);
    }
}
