package origin.consol.controller;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleScanner {
    private final String patternText;
    private final Pattern pattern;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleScanner(String patternText) {
        this.patternText = ":" + patternText + "[ _]";
        pattern = Pattern.compile(this.patternText);
    }

    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    public String start(String line) {
        return line.replaceFirst(patternText, scanner.nextLine());
    }
}
