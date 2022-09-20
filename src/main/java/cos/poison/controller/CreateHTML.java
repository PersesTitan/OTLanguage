package cos.poison.controller;

import bin.token.LoopToken;
import bin.token.VariableToken;
import cos.http.controller.HttpRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Pattern;

public class CreateHTML implements HttpRepository, LoopToken, VariableToken {
    private final String checkPattern = ML.repeat(2) + BLANK + VARIABLE_GET_S + VARIABLE_ACCESS + BLANK + MR.repeat(2);
    private final Pattern pattern = Pattern.compile(checkPattern);
    public String changeVariable(String path,
                                 Map<String, Map<String, Object>>[] repositoryArray) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8))) {
            String text;
            while ((text = reader.readLine()) != null)
                builder.append(
                        pattern.matcher(text).find()
                        ? replaceValue(text, repositoryArray)
                        : text
                        ).append("\n");
        } catch (IOException ignored) {}
        return builder.toString();
    }

    private String replaceValue(String line,
                                Map<String, Map<String, Object>>[] repositoryArray) {
        for (int i = 0; i<repositoryArray.length; i++) {
            var repository = repositoryArray[i];
            line = getValue(line, i, repository);
        }
        return line;
    }

    private String getValue(String line, int i,
                            Map<String, Map<String, Object>> repository) {
        for (var rep : repository.values()) {
            for (Map.Entry<String, Object> entry : rep.entrySet()) {
                String k = entry.getKey();
                Object v = entry.getValue();
                String key = ML.repeat(2) + BLANK + VARIABLE_GET_S + ACCESS.repeat(i) + k + BLANK + MR.repeat(2);
                line = line.replaceAll(key, v.toString());
            }
        }
        return line;
    }
}
