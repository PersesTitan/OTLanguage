package print;

import item.Check;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ScannerP implements Check {
    private static final String SPECIFIED = "ㅅㅇㅅ";
    private final String patternText = "\\sㅅㅇㅅ(\\s|$)";
    private final Pattern pattern = Pattern.compile(patternText);

    /**
     * ex) ㅇㅅㅇ 11:ㅅㅇㅅ
     * @param line 1줄 받아오기
     * @return 만약 ㅅㅇㅅ 을 포함하면
     */
    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    /**
     * @param line 라인 받아 오기
     * @return scanner 가 존재하지 않을때까지 받아오기
     */
    public String start(String line) {
        Scanner scanner = new Scanner(System.in);
        if (check(line)) {
            line = line.replaceFirst(checkBlank(line), scanner.next());
            return start(line);
        } else return line;
    }

    private String checkBlank(String line) {
        if (line.contains(" " + SPECIFIED + " ")) return " " + SPECIFIED + " ";
        else if (line.contains(" " + SPECIFIED)) return " " + SPECIFIED;
        else if (line.contains(SPECIFIED + " ")) return SPECIFIED + " ";
        else return SPECIFIED;
    }
}
