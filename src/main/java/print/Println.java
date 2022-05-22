package print;

import item.Check;
import item.PrintWork;
import item.Setting;

import java.util.regex.Pattern;

public class Println extends Setting implements Check, PrintWork {
    private static final String SPECIFIED = "ㅆㅁㅆ";
    private final String patternText = "\\n\\s*ㅆㅁㅆ\\s|^\\s*ㅆㅁㅆ\\s";
    private final Pattern pattern = Pattern.compile(patternText);

    /**
     * 시작이 ㅆㅁㅆ일때 true 를 반환함
     * @param line 1줄 받아오기
     * @return 만약 처음이 ㅆㅁㅆ 이면 println
     */
    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    /**
     * @param line 줄을 받아옴
     */
    @Override
    public void start(String line) {
        /* -- ㅆㅁㅆ 제거 -- */
        int start;
        if (line.startsWith(SPECIFIED + " "))
            start = line.indexOf(SPECIFIED)+SPECIFIED.length()+1;
        else start = line.indexOf(SPECIFIED) + SPECIFIED.length();
        line = line.substring(start);
        System.out.println(line);
    }
}
