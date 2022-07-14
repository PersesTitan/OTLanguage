package origin.loop.controller;

import event.Setting;
import origin.exception.MatchException;
import origin.exception.MatchMessage;
import origin.loop.model.LoopWork;
import origin.variable.model.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class For extends Setting implements LoopWork, Repository {
    //[숫자]^[숫자]^[숫자]
    private static final String patternText = "(\\n|^\\s*)[\\d\\-.]+\\^[\\d\\-.]+\\^[\\d\\-.]+(\\s*|$)";
    private static final Pattern pattern = Pattern.compile(patternText);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line) {
        List<String> list = Arrays.stream(line.strip().split(" "))
                .filter(Objects::nonNull)
                .filter(v -> !v.isEmpty())
                .toList();

        String key = list.get(1);
        if (!uuidMap.containsKey(key) || list.size() != 2) throw new MatchException(MatchMessage.grammarError);
        //괄호 제거 작업
        String value = uuidMap.get(key).substring(1, uuidMap.get(key).length()-1).strip();

        List<Double> numbers = Arrays.stream(list.get(0).split("\\^"))
                .mapToDouble(Double::parseDouble)
                .boxed()
                .toList();

        double first = numbers.get(0);
        double second = numbers.get(1);
        double third = numbers.get(2);

        if (third < 0) {
            for (double d = first; d > second; d += third) {
                for (String l : value.split("\\n")) super.start(l);
            }
        } else if (third > 0){
            for (double d = first; d < second; d += third) {
                for (String l : value.split("\\n")) super.start(l);
            }
        }
    }

    @Override
    public String getPattern() {
        return patternText;
    }
}
