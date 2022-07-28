package http.controller.db.controller;

import http.controller.db.define.SelectWork;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseCheck implements SelectWork {
    //?ㄷㅇㄷ?[Driver]
    private final String patternText = "\\s\\?ㄷㅇㄷ\\?\\[[^\\[\\]]+](\\s|$)";
    private final Pattern pattern = Pattern.compile(patternText);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public String start(String line) {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String group = matcher.group().trim();
            String value = group.substring(6, group.length() - 1);
            line = line.replaceFirst(patternText, checkDriver(value)?" ㅇㅇ ":" ㄴㄴ ");
        }
        return line;
    }

    private boolean checkDriver(String driver) {
        try {
            Class.forName (driver);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
