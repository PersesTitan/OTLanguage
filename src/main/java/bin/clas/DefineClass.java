package bin.clas;

import bin.token.LoopToken;
import bin.token.Token;

public record DefineClass(String className, String...methodNames) implements LoopToken {

    public String classPattern() {
        return className + ACCESS + orMerge(methodNames);
    }

    public String getMethod(String line) {
        return null;
    }
}
