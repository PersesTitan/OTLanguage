package comparison;

import item.work.ComparisonWork;

import java.util.regex.Pattern;

public class Same implements ComparisonWork {
    private final static String PATTERN = "-?\\d+\\.?\\d*\\s*ㅇ=ㅇ\\s*-?\\d+\\.?\\d*";
    private final static Pattern pattern = Pattern.compile(PATTERN);

    @Override
    public boolean start(String line) throws Exception {
        return false;
    }

    @Override
    public boolean check(String line) throws Exception {
        return pattern.matcher(line).find();
    }
}
