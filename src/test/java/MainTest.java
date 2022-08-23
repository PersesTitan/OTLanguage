import event.Setting;
import origin.variable.json.controller.json.define.JsonRepository;
import origin.variable.json.controller.json.print.PrintJson;
import origin.variable.model.Repository;

import java.util.Arrays;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;

public class MainTest implements Repository {
    PrintJson printJson = new PrintJson();

    public static void main(String[] args) {
        String total = """
                맵<-"key"<-"value"
                배열<--123
                배열<--:맵_
                배열<--null
                :맵_
                :배열_
                """;
        new MainTest(total);
    }

    MainTest(String total) {
        Setting.firstStart();
        Arrays.stream(total.split("\\n"))
                .filter(not(String::isBlank))
                .forEach(this::start);
    }

    private void start (String line) {
        for (var work : makeJsonWorks) {if (work.check(line)) work.start(line);}
        for (var work : printWorks) {if (work.check(line)) work.start(line);}
        if (printJson.check(line)) printJson.start(line);
    }
}
