package print;

import item.Check;
import item.PrintWork;

import java.util.Scanner;

public class ScannerP implements Check {

    private static final String SPECIFIED = "ㅅㅇㅅ";

    /**
     * ex) ㅇㅅㅇ 11:ㅅㅇㅅ
     * @param line 1줄 받아오기
     * @return 만약 ㅅㅇㅅ 을 포함하면
     */
    @Override
    public boolean check(String line) {
        return line.contains(SPECIFIED);
    }

    /**
     * @param line 라인 받아 오기
     * @return scanner 가 존재하지 않을때까지 받아오기
     */
    public String start(String line) {
        Scanner scanner = new Scanner(System.in);
        if (check(line)) {
            line = line.replaceFirst(SPECIFIED, scanner.next());
            return start(line);
        } else return line;
    }
}
