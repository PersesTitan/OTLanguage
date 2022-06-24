package loop;

import item.Check;
import item.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class For extends Setting implements Check {
    //[숫자]^[숫자]^[숫자]
    static final String patternText = "(\\n|^\\s)[\\d\\-.]+\\^[\\d\\-.]+\\^[\\d\\-.]+(\\s|$)";
    private static final Pattern pattern = Pattern.compile(patternText);

    public void start(String lines) throws Exception {
        List<String> list = Arrays.stream(lines.strip().split(" "))
                .filter(Objects::nonNull)
                .filter(v -> !v.isEmpty())
                .collect(Collectors.toList());

        String key = list.get(1);
        if (!uuidMap.containsKey(key) || list.size() != 2) throw new Exception("문법가 오류 발생하였습니다.");
        //괄호 제거 작업
        String value = uuidMap.get(key).substring(1, uuidMap.get(key).length()-1).strip();

        List<Double> numbers = Arrays.stream(list.get(0).split("\\^"))
                .mapToDouble(Double::parseDouble)
                .boxed()
                .collect(Collectors.toList());

        double first = numbers.get(0);
        double second = numbers.get(1);
        double third = numbers.get(2);

        if (third < 0) {
            for (double d = first; d > second; d += third) {
                for (String line : value.split("\\n")) super.start(line);
            }
        } else if (third > 0){
            for (double d = first; d < second; d += third) {
                for (String line : value.split("\\n")) super.start(line);
            }
        }
//        else {
//
//        }
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }
}
