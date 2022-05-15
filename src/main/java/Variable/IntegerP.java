package Variable;

import item.Check;

public class IntegerP implements Check {

    private static final String SPECIFIED = "ㅇㅈㅇ";

    @Override
    public boolean check(String line) {
        return line.strip().startsWith(SPECIFIED);
    }
}
