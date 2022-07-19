package origin.consol.controller;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleScanner {
    private final String patternText = "ㅅㅇㅅ";
    private final Pattern pattern = Pattern.compile(patternText);
    private final Scanner scanner = new Scanner(System.in);

    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    public String start(String line) {
        return line.replaceFirst(patternText, scanner.nextLine());
    }
}
