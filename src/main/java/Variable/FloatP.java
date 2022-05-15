package Variable;

import item.Check;

public class FloatP implements Check {

    private static final String SPECIFIED = "ㅇㅅㅇ";

    @Override
    public boolean check(String line) {
        return line.strip().startsWith(SPECIFIED);
    }
}
