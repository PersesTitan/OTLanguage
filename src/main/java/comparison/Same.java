package comparison;

import item.work.ComparisonWork;

import java.util.regex.Pattern;

public class Same implements ComparisonWork {
    private final static String PATTERN = "-?\\d+\\.?\\d*\\s*ㅇ=ㅇ\\s*-?\\d+\\.?\\d*";
    private final static Pattern pattern = Pattern.compile(PATTERN);

    @Override
    public String start(String line) throws Exception {
        return null;
    }

    @Override
    public boolean check(String line) throws Exception {
        return pattern.matcher(line).find();
    }
}
