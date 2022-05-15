package print;

import item.Check;

public class Println implements Check {

    private static final String SPECIFIED = "ㅆㅁㅆ";

    /**
     * @param line 1줄 받아오기
     * @return 만약 처음이 ㅆㅁㅆ 이면 println
     */
    @Override
    public boolean check(String line) {
        return line.strip().startsWith(SPECIFIED);
    }
}
