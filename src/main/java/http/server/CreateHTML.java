package http.server;

import http.items.HttpRepository;
import origin.variable.model.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateHTML implements HttpRepository, Repository {
    //{{ :[변수이름] }}
    private final String patternText = "\\{\\{\\s*:\\S+\\s*}}";
    private final Pattern pattern = Pattern.compile(patternText);

    //경로에 있는 html 을 읽음
    public String changeVariable(String path) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8))) {
            String text;
            while ((text = reader.readLine()) != null) {
                text = replaceVariable(text);
                builder.append(text).append("\n");
            }
        } catch (Exception ignored) {}
        return builder.toString();
    }

    //변수를 넣는 작업
    private String replaceVariable(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String variable = matcher.group();
            String var = variable.substring(2, variable.length()-2).strip().substring(1);
            if (partMap.containsKey(var))
                line = line.replaceAll(patternText, partMap.get(var));
        }
        return line;
    }
}
