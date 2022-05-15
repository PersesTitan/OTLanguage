package print;

import item.Check;

public class Print implements Check {

    private final String SPECIFIED = "ㅅㅁㅅ";

    /**
     * @param line 1줄 받아오기
     * @return 만약 처음이 ㅅㅁㅅ 이면 print
     */
    @Override
    public boolean check(String line) {
        return line.strip().startsWith(SPECIFIED);
    }
}
