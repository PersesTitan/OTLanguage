package origin.math;

import java.util.regex.Pattern;

public class MoreSmall implements MathWork {
    //ㅅㅎ[숫자]>[숫자]ㅅㅎ
    private final Pattern pattern = Pattern.compile("(^|\\s+)ㅅㅎ-?\\.?\\d+>-?\\.?\\d+ㅎㅅ($|\\s+)");

    @Override
    public String start(String line) throws Exception {
        return null;
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }
}
