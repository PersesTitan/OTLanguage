package Variable;

import item.Check;

public class DoubleP implements Check {

    private static final String SPECIFIED = "ㅇㅆㅇ";

    @Override
    public boolean check(String line) {
        return line.strip().startsWith(SPECIFIED);
    }
}
