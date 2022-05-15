package Variable;

import item.Check;

public class StringP implements Check {

    private static final String SPECIFIED = "ㅇㅁㅇ";

    @Override
    public boolean check(String line) {
        return line.strip().startsWith(SPECIFIED);
    }
}