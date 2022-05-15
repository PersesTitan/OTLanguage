package print;

import item.Check;

public class Scanner implements Check {

    private static final String SPECIFIED = "ㅅㅇㅅ";

    /**
     * @param line 1줄 받아오기
     * @return 만약 처음이 ㅅㅇㅅ 이면 Scanner
     */
    @Override
    public boolean check(String line) {
        return line.strip().startsWith(SPECIFIED);
    }
}
