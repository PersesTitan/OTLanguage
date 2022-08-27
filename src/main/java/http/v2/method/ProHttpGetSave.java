package http.v2.method;

import event.token.Token;
import origin.loop.model.LoopWork;
import tool.Tools;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class ProHttpGetSave implements Token, LoopWork {
    private final String[] patterns;
    private final String patternText;
    private final Pattern pattern;

    public ProHttpGetSave(String patternText) {
        // ㅅㅂㅅ [..][..]
        this.patterns = new String[]{START, patternText, BLANK, PARAMETER, "{2,3}"};
        this.patternText = Token.makeBlank(patterns);
        // ㅅㅂㅅ [..][..] {...} => file Name
        this.pattern = Pattern.compile(Token.makeBlank(this.patternText, BLANK, UUID, "=>", ALL));
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line,
                      Map<String, Map<String, Object>> repository,
                      Set<String> set) {

    }

    @Override
    public String getPattern() {
        return patternText;
    }

    private void cutting(String line) {
        line = Tools.deleteBothEnds(line);
        String[] params = line.split("]\\[");

    }
}
