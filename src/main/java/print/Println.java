package print;

import item.Check;
import item.PrintWork;

public class Println implements Check, PrintWork {

    private static final String SPECIFIED = "ㅆㅁㅆ";

    /**
     * @param line 1줄 받아오기
     * @return 만약 처음이 ㅆㅁㅆ 이면 println
     */
    @Override
    public boolean check(String line) {
        return line.strip().startsWith(SPECIFIED);
    }

    @Override
    public void start(String line) {
        String value = "";

        System.out.println(value);
    }
}
