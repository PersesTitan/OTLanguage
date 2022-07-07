package print;

import item.work.PrintWork;

import java.util.regex.Pattern;

public class Println implements PrintWork {
    private static final String SPECIFIED = "ㅆㅁㅆ";
    private final String patternText = "(\\n|^)\\s*ㅆㅁㅆ($|\\s)";
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
        int start = line.indexOf(SPECIFIED)+SPECIFIED.length();
        if (line.strip().startsWith(SPECIFIED + " ")) start += 1;
        line = line.substring(start);
        System.out.println(line);
    }
}
