package Calculation;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Account {
    /**
     * @param line 라인 받아오기
     * @return 쪼갠 후 값을 리턴
     * @throws Exception 기호를 읽을 수 없으면 에러 발생
     */
    public double account(@NotNull String line) throws Exception {
        String[] number = line.split("ㅇ\\+ㅇ|ㅇ-ㅇ|ㅇ\\*ㅇ|ㅇ/ㅇ|ㅇ%ㅇ");
        String[] sing = line.split("[0-9]|\\.");
        List<String> numbers = new ArrayList<>(Arrays.asList(number));
        List<String> sings = new ArrayList<>(Arrays.asList(sing));
        numbers.removeAll(Collections.singletonList(""));
        numbers.removeAll(Collections.singletonList(null));
        sings.removeAll(Collections.singletonList(""));
        sings.removeAll(Collections.singletonList(null));
        return account(numbers, sings);
    }

    /**
     * @param numbers 숫자값
     * @param sings 기호들
     * @return 계산후 값을 반환함
     * @throws Exception 만약에 읽을 수 없는 기호가 존재할 시 에러 발생
     */
    public double account(@NotNull List<String> numbers, @NotNull List<String> sings) throws Exception {
        assert sings.size() + 1 == numbers.size();
        double total = Double.parseDouble(numbers.get(0));
        for (int i = 1; i<numbers.size(); i++) {
            String value = sings.get(i-1);
            if (value.startsWith("+")) value = value.substring(1);
            if (value.endsWith("+")) value = value.substring(0, value.lastIndexOf("+"));
            double d = Double.parseDouble(numbers.get(i));
            switch (value) {
                case "ㅇ+ㅇ" -> total += d;
                case "ㅇ-ㅇ" -> total -= d;
                case "ㅇ*ㅇ" -> total *= d;
                case "ㅇ/ㅇ" -> total /= d;
                case "ㅇ%ㅇ" -> total %= d;
                case "ㅇ+ㅇ-" -> total += (-d);
                case "ㅇ-ㅇ-" -> total -= (-d);
                case "ㅇ*ㅇ-" -> total *= (-d);
                case "ㅇ/ㅇ-" -> total /= (-d);
                case "ㅇ%ㅇ-" -> total %= (-d);
                case "-ㅇ+ㅇ" -> total = (-total) + d;
                case "-ㅇ-ㅇ" -> total = (-total) - d;
                case "-ㅇ*ㅇ" -> total = (-total) * d;
                case "-ㅇ/ㅇ" -> total = (-total) / d;
                case "-ㅇ%ㅇ" -> total = (-total) % d;
                case "-ㅇ+ㅇ-" -> total = (-total) + (-d);
                case "-ㅇ-ㅇ-" -> total = (-total) - (-d);
                case "-ㅇ*ㅇ-" -> total = (-total) * (-d);
                case "-ㅇ/ㅇ-" -> total = (-total) / (-d);
                case "-ㅇ%ㅇ-" -> total = (-total) % (-d);
                default -> throw new Exception(value + "는 계산식이 아닙니다.");
            }
        } return total;
    }
}
