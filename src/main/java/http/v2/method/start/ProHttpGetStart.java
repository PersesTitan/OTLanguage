package http.v2.method.start;

import event.Setting;
import event.token.Token;
import tool.Tools;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record ProHttpGetStart(String total) implements Token {

    public void start(Map<String, Object> parameters,
                      Map<String, Map<String, Object>> repository,
                      Set<String> set) {
        for (String line : this.total.split("\\n")) {
            line = getParams(line, parameters);
            Setting.start(line, repository, set);
        }
    }

    private String getParams(String line,
                                   Map<String, Object> parameters) {
        Matcher matcher = Pattern.compile(HTTP_PARM).matcher(line);
        while (matcher.find()) {
            String group = matcher.group();
            // ㅅㅂㅅ~key => key
            String variable = Tools.deleteBothEnds(group)
                    .replaceFirst("^" + HTTP + "~", "");
            Object value = parameters.getOrDefault(variable, group);
            line = line.replace(Token.replacePattern(group), value.toString());
        }
        return line;
    }
}
