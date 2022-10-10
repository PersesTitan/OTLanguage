package bin.define.method;

import bin.token.LoopToken;
import work.ReturnWork;

import java.util.Map;

public class MethodVoid implements LoopToken, ReturnWork {

    public MethodVoid() {
        
    }

    @Override
    public boolean check(String line) {
        return false;
    }

    @Override
    public String start(String line,
                        Map<String, Map<String, Object>>[] repositoryArray) {
        return null;
    }
}
