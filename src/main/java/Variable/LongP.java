package Variable;

import item.Check;

public class LongP implements Check {

    private static final String SPECIFIED = "ㅇㅉㅇ";

    @Override
    public boolean check(String line) {
        return line.strip().startsWith(SPECIFIED);
    }
}
