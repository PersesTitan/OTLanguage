package Variable;

import item.Check;
import item.VariableWork;

public class BooleanP implements Check, VariableWork {

    private static final String SPECIFIED = "ㅇㅂㅇ";

    @Override
    public boolean check(String line) {
        return line.strip().startsWith(SPECIFIED);
    }

    @Override
    public void start(String line) {

    }
}
