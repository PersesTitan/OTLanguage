package Variable;

import item.Check;

public class CharacterP implements Check {

    private static final String SPECIFIED = "ㅇㄱㅇ";

    @Override
    public boolean check(String line) {
        return line.strip().startsWith(SPECIFIED);
    }
}
