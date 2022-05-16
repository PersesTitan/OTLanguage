package print;

import item.Check;
import item.PrintWork;
import item.Setting;

import java.util.List;

public class Print extends Setting implements Check, PrintWork {

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
        /* --ㅅㅁㅅ 제거-- */
        int start = line.indexOf(SPECIFIED) + SPECIFIED.length();
        line = line.substring(start).strip();
        if (variable.check(line)) {
            List<String> lists = variable.getVar(line);
            for (String list : lists)
                line = line.replaceFirst(list, checkValue(list));
        }
        System.out.print(line);
    }
}
