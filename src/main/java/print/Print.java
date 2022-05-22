package print;

import item.Check;
import item.PrintWork;
import item.Setting;

import java.util.regex.Pattern;

public class Print extends Setting implements Check, PrintWork {
    private static final String SPECIFIED = "ㅅㅁㅅ";
    private final String patternText = "\\n\\s*ㅅㅁㅅ\\s|^\\s*ㅅㅁㅅ\\s";
    private final Pattern pattern = Pattern.compile(patternText);

    /**
     * @param line 1줄 받아오기
     * @return 만약 처음이 ㅅㅁㅅ 이면 print
     */
    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line) {
        /* --ㅅㅁㅅ 제거-- */
        int start;
        if (line.startsWith(SPECIFIED + " ")) start = line.indexOf(SPECIFIED)+SPECIFIED.length()+1;
        else start = line.indexOf(SPECIFIED) + SPECIFIED.length();
        line = line.substring(start).trim();
        System.out.print(line);
    }
}
