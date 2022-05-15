package print;

import item.Check;
import item.PrintWork;

public class Print implements Check, PrintWork {

    private static final String SPECIFIED = "ㅅㅁㅅ";

    /**
     * @param line 1줄 받아오기
     * @return 만약 처음이 ㅅㅁㅅ 이면 print
     */
    @Override
    public boolean check(String line) {
        return line.strip().startsWith(SPECIFIED);
    }

    @Override
    public void start(String line) {
        String value = "";

        System.out.print(value);
    }
}
